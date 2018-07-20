(ns fulcrologic.input
  (:require [devcards.core :refer-macros [defcard-doc defcard]]
            [fulcro.client.cards :refer [defcard-fulcro make-root]]
            [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro.client.mutations :as m]
            [fulcro.client.dom :as dom]
            [fulcrologic.semantic-ui.icons :as i]
            [fulcrologic.semantic-ui.factories :as f]))

(defsc SomeInputs [this {:keys [name address]}]
  {:query         [:name :address]
   :initial-state {:name "" :address ""}
   :ident         (fn [] [:component/by-id :INPUTS])}
  (dom/div
    "Name:"
    (f/ui-input {:value name :onChange #(m/set-string! this :name :event %)})
    (dom/br)
    "Address:"
    (f/ui-input {:value address :onChange #(m/set-string! this :address :event %)})))

(defcard-fulcro some-inputs
  "This card demonstrates inputs. They are wrapped so that cursor jumping does not happen."
  (make-root SomeInputs {})
  {}
  {:inspect-data true})

