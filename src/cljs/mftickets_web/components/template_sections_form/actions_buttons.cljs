(ns mftickets-web.components.template-sections-form.actions-buttons
  (:require [mftickets-web.components.button :as components.button]))

(def base-class "template-section-form-action-buttons")

(defn- remove-button
  [{:template-section-form.action-buttons.messages/keys [on-remove-section]}]
  (let [props {:button/label "Remove"
               :button/style :button/danger
               :button.messages/on-click on-remove-section}]
    [components.button/button props]))

(defn template-section-form-action-buttons
  [props]
  [:div {:class base-class}
   [remove-button props]])
