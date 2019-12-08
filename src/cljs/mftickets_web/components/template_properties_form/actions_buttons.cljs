(ns mftickets-web.components.template-properties-form.actions-buttons
  (:require [mftickets-web.components.button :as components.button]))

;; Css
(def ^:private base-class "template-properties-form-actions-buttons")

;; Globals
(def ^:private label "Remove")
(def ^:private style :button/danger)

(defn- remove-button
  [{:template-properties-form.actions-buttons.messages/keys [on-remove-property]}]
  (let [props {:button/label label
               :button/style style
               :button.messages/on-click on-remove-property}]
    [components.button/button props]))

(defn template-properties-form-actions-buttons [props]
  [:div {:class base-class}
   [remove-button props]])
