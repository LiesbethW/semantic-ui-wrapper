(ns fulcrologic.elements.divider
  (:require [devcards.core :refer-macros [defcard-doc defcard]]
            [fulcro.client.cards :refer [defcard-fulcro make-root]]
            [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro.client.mutations :as m]
            [fulcro.client.dom :as dom]
            [fulcrologic.semantic-ui.icons :as i]
            [fulcrologic.semantic-ui.factories :as f]))

(defcard Divider
  "A standard divider."
  (f/ui-divider))

(defcard VerticalDivider
  "A divider can segment content vertically."
  (f/ui-grid {:columns 3 :relaxed true}
    (f/ui-grid-column nil
      (f/ui-segment {:basic true} "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec odio."))
    (f/ui-divider {:vertical true} "Or")
    (f/ui-grid-column nil
      (f/ui-segment {:basic true} "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec odio."))
    (f/ui-divider {:vertical true} "Or")
    (f/ui-grid-column nil
      (f/ui-segment {:basic true} "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec odio."))))

(defcard HorizontalDivider
  "A divider can segment content horizontally."
  (f/ui-segment {:padded true}
    (f/ui-button {:primary true :fluid true} "Login")
    (f/ui-divider {:horizontal true} "or")
    (f/ui-button {:secondary true :fluid true} "Sign Up Now")))