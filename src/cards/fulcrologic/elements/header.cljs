(ns fulcrologic.elements.header
  (:require [devcards.core :refer-macros [defcard-doc defcard]]
            [fulcro.client.cards :refer [defcard-fulcro make-root]]
            [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro.client.mutations :as m]
            [fulcro.client.dom :as dom]
            [fulcrologic.semantic-ui.icons :as i]
            [fulcrologic.semantic-ui.factories :as f]))

(defcard PageHeaders
  "Headers may be oriented to give the hierarchy of a section in the context of the page.
  *Page headings are sized using rem and are not affected by surrounding content size.*"
  (dom/div nil
    (f/ui-header {:as :h1} "First Header")
    (f/ui-header {:as :h2} "Second Header")
    (f/ui-header {:as :h3} "Third Header")
    (f/ui-header {:as :h4} "Fourth Header")
    (f/ui-header {:as :h5} "Fifth Header")
    (f/ui-header {:as :h6} "Sixth Header")))

(defcard ContentHeaders
  "Headers may be oriented to give the importance of a section.
  *Content headings are sized with em and are based on the font-size of their container.*"
  (dom/div nil
    (f/ui-header {:size :huge} "Huge Header")
    (f/ui-header {:size :large} "Large Header")
    (f/ui-header {:size :medium} "Medium Header")
    (f/ui-header {:size :small} "Small Header")
    (f/ui-header {:size :tiny} "Tiny Header")))

(defcard IconHeaders
  "A header can be formatted to emphasize an icon."
  (f/ui-header {:as :h2 :icon true}
    (f/ui-icon {:name i/settings-icon})
    "Account Settings"
    (f/ui-header-subheader nil "Manage your account settings and set e-mail preferences.")))

(defcard HeaderExampleUsersIcon
  (dom/div nil
    (f/ui-header {:as :h2 :icon true :textAlign :center}
      (f/ui-icon {:name i/users-icon :circular true})
      (f/ui-header-content nil "Friends"))
    (f/ui-image {:centered true :size :large :src "https://react.semantic-ui.com/images/wireframe/centered-paragraph.png"})))

(defcard Subheaders
  "Headers may be formatted to label smaller or de-emphasized content."
  (dom/div nil
    (f/ui-header {:sub true} "Price")
    (dom/span nil "$10.99")))

(defcard Image
  "A header may contain an image."
  (f/ui-header {:as :h2}
    (f/ui-image {:circular true :src "https://react.semantic-ui.com/images/avatar/large/patrick.png"})
    "Patrick"))

(defcard Variations
  (f/ui-segment-group nil
    (f/ui-segment nil
      (f/ui-header {:as :h3 :dividing true} "Dividing Header"))
    (f/ui-segment nil
      (f/ui-header {:as :h3 :block true} "Block Header"))
    (f/ui-segment nil
      (f/ui-header {:as :h2 :attached :top} "Attached Header")
      (f/ui-segment {:attached true}
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
        laboris nisi ut aliquip ex ea commodo consequat."))
    (f/ui-segment {:clearing true}
      (f/ui-header {:as :h2 :floated :right} "Float Right")
      (f/ui-header {:as :h2 :floated :left} "Float Left"))
    (f/ui-segment nil
      (f/ui-header {:as :h3 :textAlign :right} "Aligned Right")
      (f/ui-header {:as :h3 :textAlign :left} "Aligned Left")
      (f/ui-header {:as :h3 :textAlign :justified} "This text takes up the full width of the container")
      (f/ui-header {:as :h3 :textAlign :center} "Centered"))))
