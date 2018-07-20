(ns fulcrologic.elements.flag
  (:require [devcards.core :refer-macros [defcard-doc defcard]]
            [fulcro.client.cards :refer [defcard-fulcro make-root]]
            [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro.client.mutations :as m]
            [fulcro.client.dom :as dom]
            [fulcrologic.semantic-ui.icons :as i]
            [fulcrologic.semantic-ui.factories :as f]))

(defcard Flag
  "A flag can use the two digit country code, the full name, or a common alias. N.B.: These must all be lower case.\n For all the flags, refer to the [SUI docs](https://semantic-ui.com/elements/flag)"
  (f/ui-segment nil
    (f/ui-flag {:name "ae"})
    (f/ui-flag {:name "france"})
    (f/ui-flag {:name "myanmar"})))
