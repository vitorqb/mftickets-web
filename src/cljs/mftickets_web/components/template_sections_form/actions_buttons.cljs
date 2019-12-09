(ns mftickets-web.components.template-sections-form.actions-buttons
  (:require [mftickets-web.components.button :as components.button]))

;; Scss
(def base-class "template-section-form-action-buttons")

;; Globals
(def ^:private remove-btn-label "Remove")
(def ^:private remove-btn-style :button/danger)
(def ^:private add-property-btn-label "Add Property")
(def ^:private add-property-btn-style nil)

;; Components
(defn- remove-button
  [{:template-sections-form.action-buttons.messages/keys [on-remove-section]}]

  {:pre [(ifn? on-remove-section)]}
  
  (let [props {:button/label remove-btn-label
               :button/style remove-btn-style
               :button.messages/on-click on-remove-section}]
    [components.button/button props]))

(defn- add-property-button
  [{:template-sections-form.action-buttons.messages/keys [on-add-property]}]

  {:pre [(ifn? on-add-property)]}
  
  (let [props {:button/label add-property-btn-label
               :button/style add-property-btn-style
               :button.messages/on-click on-add-property}]
    [components.button/button props]))

(defn template-section-form-action-buttons
  [props]
  [:div {:class base-class}
   [remove-button props]
   [add-property-button props]])
