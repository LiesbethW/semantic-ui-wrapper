(ns fulcrologic.modules.popup
  (:require [devcards.core :refer-macros [defcard-doc defcard]]
            [fulcro.client.cards :refer [defcard-fulcro make-root]]
            [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro.client.mutations :as m :refer [defmutation]]
            [fulcro.client.dom :as dom]
            [fulcrologic.semantic-ui.icons :as i]
            [fulcrologic.semantic-ui.factories :as f]))

(defcard Popup
  (f/ui-popup {:trigger (f/ui-button {:icon i/add-icon})
               :content "Add users to your feed"}))

(defcard HtmlPopup
  (let [card (f/ui-card nil
               (f/ui-image {:src "https://react.semantic-ui.com/images/movies/totoro-horizontal.jpg"})
               (f/ui-card-content nil
                 (f/ui-card-header nil "My Neighbor Totoro")
                 (f/ui-card-description nil
                   "Two sisters move to the country with their father in order to be closer to their
                   hospitalized mother, and discover the surrounding trees are inhabited by magical spirits.")))]
    (f/ui-popup {:trigger card}
      (f/ui-popup-header nil "User Rating")
      (f/ui-popup-content nil
        (f/ui-rating {:icon i/star-icon
                      :defaultRating 3
                      :maxRating 4})))))


