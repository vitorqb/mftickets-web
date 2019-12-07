(ns mftickets-web.components.template-form.sections-actions-buttons
  (:require [mftickets-web.components.button :as components.button]))

;; Globals
(def ^:private add-section-label "Add Section")

;; Css
(def ^:private base-class "template-form-sections-actions-buttons")

;; Components
(defn- add-button
  [{:template-form.sections-actions-buttons.messages/keys [on-add-template-section]}]

  {:pre [(ifn? on-add-template-section)]}
  
  [components.button/button {:button/label add-section-label
                             :button.messages/on-click on-add-template-section}])

(defn template-form-sections-actions-buttons
  [props]
  [:div {:class [base-class]}
   [add-button props]])
