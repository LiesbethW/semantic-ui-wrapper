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


(defmutation submit-form [{:keys [id diff]}]
  (action [{:keys [state]}]
          (console/log diff)
          (swap! state (fn [s]
                         (-> s
                             (fs/entity->pristine* [:form/by-id id])
                             (assoc :form nil))))))

(s/def :form/first-name (s/and string? #(seq (clojure.string/trim %))))
(s/def :form/agreed-to-conditions? true?)

(defn render-field [component field renderer]
  (let [form         (prim/props component)
        entity-ident (prim/get-ident component form)
        id           (str (first entity-ident) "-" (second entity-ident))
        is-dirty?    (fs/dirty? form field)
        validity     (fs/get-spec-validity form field)
        is-invalid?  (= :invalid validity)
        value        (get form field "")]
    (renderer {:dirty?   is-dirty?
               :ident    entity-ident
               :id       id
               :invalid? is-invalid?
               :value    value})))

(defn text-field-with-label [component field field-label]
  (render-field component field
                (fn [{:keys [invalid? id dirty? value ident]}]
                  (f/ui-form-input {:value    value
                                    :id       id
                                    :error    invalid?
                                    :onBlur   #(prim/transact! component `[(fs/mark-complete! {:entity-ident ~ident
                                                                                               :field        ~field})
                                                                           :root/person])
                                    :onChange #(m/set-string! component field :event %)
                                    :label    field-label
                                    :placeholder field-label}))))


(defsc FormWithValidation [this {:keys [form/first-name form/last-name form/agreed-to-conditions?] :as props}]
  {:query [:form/first-name :form/last-name :form/agreed-to-conditions? fs/form-config-join]
   :initial-state (fn [_p]
                    {:form/first-name ""
                     :form/last-name ""
                     :form/agreed-to-conditions? false})
   :form-fields #{:form/first-name :form/last-name :form/agreed-to-conditions?}
   :ident (fn [] [:form/by-id :form-with-validation])}
  (f/ui-form nil
    (f/ui-form-group {:widths :equal}
      (text-field-with-label this :form/first-name "First Name")
      (f/ui-form-input {:label "Last Name"
                        :placeholder "Last Name"
                        :value last-name
                        :onChange (fn [evt] (m/set-string! this :form/last-name :event evt))
                        :onBlur #(prim/transact! this `[(fs/mark-complete! {:entity-ident [:form/by-id :form-with-validation]
                                                                            :field :form/last-name})])}))
    (f/ui-form-checkbox {:label "I agree to the Terms and Conditions"
                         :checked agreed-to-conditions?
                         :onChange (fn [evt] (prim/transact! this `[(m/toggle {:field :form/agreed-to-conditions?})
                                                                    (fs/mark-complete! {:entity-ident [:form/by-id :form-with-validation]
                                                                                        :field :form/agreed-to-conditions?})]))})
    (f/ui-form-group nil
      (f/ui-form-button {:onClick #(prim/transact! this `[(fs/reset-form! {:form-ident [:form/by-id :form-with-validation]})])
                         :disabled (not (fs/dirty? props))}
                        "Reset")
      (f/ui-form-button {:onClick #(prim/transact! this `[(submit-form {:id :form-with-validation
                                                                        :diff ~(fs/dirty-fields props true)})
                                                          :form])
                         :disabled (or
                                     (fs/invalid-spec? props)
                                     (not (fs/dirty? props)))}
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

(defsc CheckboxesFormRoot [this {:keys [form]}]
  {:query [{:form (prim/get-query FormWithValidation)}]
   :initial-state (fn [_] {})}
  (f/ui-segment nil
    (if (:form/first-name form)
      (ui-form-with-validation form)
      (f/ui-button {:onClick #(prim/transact! this `[(new-form {})])} "New form"))))

(defcard-fulcro checkboxes-form-live
  CheckboxesFormRoot
  {}
  {:inspect-data true})
