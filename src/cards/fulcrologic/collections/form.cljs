(ns fulcrologic.collections.form
  (:require [devcards.core :refer-macros [defcard-doc defcard]]
            [fulcro.client.cards :refer [defcard-fulcro make-root]]
            [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro.client.mutations :as m :refer [defmutation]]
            [fulcro.client.dom :as dom]
            [fulcro.ui.form-state :as fs]
            [fulcro.server :as server]
            [fulcrologic.semantic-ui.icons :as i]
            [fulcrologic.semantic-ui.factories :as f]
            [cljs.spec.alpha :as s]))


(defmutation submit-form [{:keys [diff]}]
  (action [{:keys [state ref]}]
          (println diff)
          (swap! state (fn [s]
                         (-> s
                             (fs/entity->pristine* ref)
                             (assoc :form nil))))))

(defn submit-form! [component props mutation-params]
  (let [ident (prim/get-ident component)]
    (prim/transact! component `[(fs/mark-complete! {:entity-ident ~ident})])
    (when (and
            (fs/dirty? props)
            (not (fs/invalid-spec? props)))
      (prim/transact! component `[(submit-form ~mutation-params)
                                  (fs/reset-form! {:form-ident [:form/by-id :form-with-validation]})
                                  (fs/clear-complete! {:entity-ident [:form/by-id :form-with-validation]})]))))




(s/def :form/first-name (s/and string? #(seq (clojure.string/trim %))))
(s/def :form/agreed-to-conditions? true?)

(defn render-field [component field default renderer]
  (let [form         (prim/props component)
        entity-ident (prim/get-ident component form)
        id           (str (first entity-ident) "-" (second entity-ident))
        is-dirty?    (fs/dirty? form field)
        validity     (fs/get-spec-validity form field)
        is-invalid?  (= :invalid validity)
        value        (get form field default)]
    (renderer {:dirty?   is-dirty?
               :ident    entity-ident
               :id       id
               :invalid? is-invalid?
               :value    value})))

(defn text-field-with-label [component field field-label error-message]
  (render-field component field ""
                (fn [{:keys [invalid? id dirty? value ident]}]
                  (let [input  (f/ui-input {:value    value
                                            :id       id
                                            :onBlur   #(prim/transact! component `[(fs/mark-complete! {:entity-ident ~ident
                                                                                                       :field        ~field})])
                                            :onChange #(m/set-string! component field :event %)
                                            :placeholder field-label})]

                    (f/ui-form-field {:error    invalid?}
                      (dom/label nil field-label)
                      (f/ui-popup {:trigger input
                                   :open invalid?
                                   :content error-message
                                   :position "top center"
                                   :size :small}))))))

(defn checkbox-with-label [component field field-label error-message]
  (render-field component field false
                (fn [{:keys [invalid? id dirty? value ident]}]
                  (let [input (f/ui-form-checkbox {:error    invalid?
                                                   :label    field-label
                                                   :checked  value
                                                   :onChange (fn [evt] (prim/transact! component `[(m/toggle {:field ~field})
                                                                                                   (fs/mark-complete! {:entity-ident ~ident
                                                                                                                       :field ~field})]))})]
                    (f/ui-form-field {:error invalid?}
                      (f/ui-popup {:trigger input
                                   :open invalid?
                                   :content error-message
                                   :position "top center"
                                   :size :small}))))))

(f/ui-checkbox)

(defsc FormWithValidation [this {:keys [form/first-name form/last-name form/agreed-to-conditions?] :as props}]
  {:query         [:form/first-name :form/last-name :form/agreed-to-conditions? fs/form-config-join]
   :initial-state (fn [_p]
                    {:form/first-name            ""
                     :form/last-name             ""
                     :form/agreed-to-conditions? false})
   :form-fields   #{:form/first-name :form/last-name :form/agreed-to-conditions?}
   :ident         (fn [] [:form/by-id :form-with-validation])}
  (f/ui-form nil
    (f/ui-form-group {:widths :equal}
      (text-field-with-label this :form/first-name "First Name" "Please fill in a first name")
      (text-field-with-label this :form/last-name "Last Name" ""))
    (checkbox-with-label this :form/agreed-to-conditions? "I agree to the Terms and Conditions" "Please review the Terms and Conditions")
    (f/ui-form-group nil
      (f/ui-form-button {:onClick  #(prim/transact! this `[(fs/reset-form! {:form-ident [:form/by-id :form-with-validation]})
                                                           (fs/clear-complete! {:entity-ident [:form/by-id :form-with-validation]})])
                         :disabled (not (fs/dirty? props))}
                        "Reset")
      (f/ui-form-button {:onClick #(submit-form! this props {:diff (fs/dirty-fields props true)})}
                        "Submit"))))

(def ui-form-with-validation (prim/factory FormWithValidation))

(defn add-form*
  "Add a form to the state db"
  [state-map ident]
  (let [form (prim/get-initial-state FormWithValidation {})]
    (assoc-in state-map ident form)))

(defmutation new-form [_]
  (action [{:keys [state]}]
          (let [form-ident [:form/by-id :form-with-validation]]
            (swap! state
                   (fn [s] (-> s
                               (add-form* form-ident)
                               (assoc :form form-ident)
                               (fs/add-form-config* FormWithValidation form-ident)))))))

(defsc ErrorsFormRoot [this {:keys [form]}]
  {:query [{:form (prim/get-query FormWithValidation)}]
   :initial-state (fn [_] {})}
  (f/ui-segment nil
    (if (:form/first-name form)
      (ui-form-with-validation form)
      (f/ui-button {:onClick #(prim/transact! this `[(new-form {})])} "New form"))))

(defcard-fulcro errors-form-live
  ErrorsFormRoot
  {}
  {:inspect-data true})
