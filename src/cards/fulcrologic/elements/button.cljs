(ns fulcrologic.elements.button
  (:require [devcards.core :refer-macros [defcard-doc defcard]]
            [fulcro.client.cards :refer [defcard-fulcro make-root]]
            [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro.client.mutations :as m]
            [fulcro.client.dom :as dom]
            [fulcrologic.semantic-ui.icons :as i]
            [fulcrologic.semantic-ui.factories :as f]))

(defcard StandardButton
  (f/ui-button nil "Click Here"))

(defcard ShorthandButton
  (f/ui-button {:content "Click Here"}))

(defcard Emphasis
  (dom/div
    (f/ui-button {:primary true} "Primary")
    (f/ui-button {:secondary true} "Secondary")))

(defcard Animated
  (dom/div
    (f/ui-button {:animated true}
      (f/ui-button-content {:visible true} "Next")
      (f/ui-button-content {:hidden true}
        (f/ui-icon {:name i/arrow-right-icon})))
    (f/ui-button {:animated :vertical}
      (f/ui-button-content {:hidden true} "Shop")
      (f/ui-button-content {:visible true}
        (f/ui-icon {:name i/shop-icon})))
    (f/ui-button {:animated :fade}
      (f/ui-button-content {:visible true} "Sign up for a Pro account")
      (f/ui-button-content {:hidden true} "$12.99 a month"))))

(defcard Labeled
  (dom/div
    (f/ui-button {:as :div :labelPosition :right}
      (f/ui-button {:icon true}
        (f/ui-icon {:name i/heart-icon})
        "Like")
      (f/ui-label {:as :a :basic true :pointing :left}
        2.048))
    (f/ui-button {:as :div :labelPosition :left}
      (f/ui-label {:as :a :basic true :pointing :right}
        2.048)
      (f/ui-button {:icon true}
        (f/ui-icon {:name i/heart-icon})
        "Like"))
    (f/ui-button {:as :div :labelPosition :left}
      (f/ui-label {:as :a :basic true}
        2.048)
      (f/ui-button {:icon true}
        (f/ui-icon {:name i/fork-icon})))))

(defcard LabeledShorthand
  (f/ui-button {:content       "Like"
                :icon          i/heart-icon
                :label         {:as "a" :basic true :content "2,000"}
                :labelPosition "left"}))

(defcard Icon
  (dom/div
    (f/ui-button {:icon true}
      (f/ui-icon {:name i/world-icon}))
    (f/ui-button {:icon i/world-icon})))

(defcard Group
  (f/ui-button-group nil
    (f/ui-button nil "A")
    (f/ui-button nil "B")
    (f/ui-button nil "C")))

(defcard IconGroup
  (f/ui-button-group nil
    (f/ui-button {:icon true}
      (f/ui-icon {:name i/align-left-icon}))
    (f/ui-button {:icon true}
      (f/ui-icon {:name i/align-center-icon}))
    (f/ui-button {:icon true}
      (f/ui-icon {:name i/align-right-icon}))
    (f/ui-button {:icon true}
      (f/ui-icon {:name i/align-justify-icon}))))

(defcard Conditionals
  (f/ui-button-group nil
    (f/ui-button {} "Cancel")
    (f/ui-button-or nil nil)
    (f/ui-button {:positive true} "Save")))
