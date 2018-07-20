(ns fulcrologic.elements.icon
  (:require [devcards.core :refer-macros [defcard-doc defcard]]
            [fulcro.client.cards :refer [defcard-fulcro make-root]]
            [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro.client.mutations :as m]
            [fulcro.client.dom :as dom]
            [fulcrologic.semantic-ui.icons :as i]
            [fulcrologic.semantic-ui.factories :as f]))

(defcard Disabled
  "An icon can show that it is disabled."
  (f/ui-icon {:disabled true :name i/users-icon}))

(defcard Loading
  "An icon can be used as a simple loader."
  (dom/div
    (f/ui-icon {:loading true :name i/spinner-icon})
    (f/ui-icon {:loading true :name i/certificate-icon})
    (f/ui-icon {:loading true :name i/asterisk-icon})))

(defcard Size
  "An icon can vary in size."
  (dom/div nil
    (map #(f/ui-icon {:name i/home-icon :size %})
         [:mini :tiny :small])
    (f/ui-icon {:name i/home-icon})
    (map #(f/ui-icon {:name i/home-icon :size %})
         [:large :big :huge :massive])))

(defcard Link
  "An icon can be formatted as a link"
  (dom/div nil
    (f/ui-icon {:link true :name i/close-icon})
    (f/ui-icon {:link true :name i/help-icon})))

(defcard Flipped
  "An icon can be flipped"
  (dom/div nil
           (f/ui-icon {:name i/cloud-icon})
           (f/ui-icon {:name i/cloud-icon :flipped :horizontally})
           (f/ui-icon {:name i/cloud-icon :flipped :vertically})))

(defcard Rotated
  "An icon can be rotated"
  (dom/div nil
           (f/ui-icon {:name i/cloud-icon :rotated :clockwise})
           (f/ui-icon {:name i/cloud-icon :rotated :counterclockwise})))

(defcard Circular
  "An icon can be formatted to appear circular."
  (dom/div nil
    (f/ui-icon {:circular true :name :users})
    (f/ui-icon {:circular true :name :users :color :teal})
    (f/ui-icon {:circular true :name :users :inverted true})
    (f/ui-icon {:circular true :name :users :inverted true :color :teal})))

(defcard Groups
  "Several icons can be used together as a group"
  (dom/div nil
    (dom/div nil
      (f/ui-icon-group {:size :huge}
        (f/ui-icon {:size :big :name i/circle-thin-icon})
        (f/ui-icon {:name i/user-icon})))
    (dom/div nil
      (f/ui-icon-group {:size :huge}
        (f/ui-icon {:size :big :color :red :name i/dont-icon})
        (f/ui-icon {:name i/user-icon}))
      (f/ui-icon-group {:size :huge}
        (f/ui-icon {:size :big :name i/sun-icon :loading true})
        (f/ui-icon {:name i/user-icon})))))

(defcard CornerIcon
  "A group of icons can display a smaller corner icon."
  (dom/div nil
    (dom/div nil
      (f/ui-icon-group {:size :huge}
        (f/ui-icon {:name i/puzzle-icon})
        (f/ui-icon {:corner true :name i/add-icon})))
    (dom/br)
    (dom/div nil
      (f/ui-header {:as :h2}
        (f/ui-icon-group {:size :large}
          (f/ui-icon {:name i/twitter-icon})
          (f/ui-icon {:corner true :name i/add-icon}))
        "Add on Twitter"))))