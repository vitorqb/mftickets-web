(ns mftickets-web.components.template-properties-form.actions-buttons
  (:require [mftickets-web.components.button :as components.button]
            [mftickets-web.components.factories.input :as factories.input]))

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

(defn- move-back-button
  [{:template-properties-form.actions-buttons.messages/keys [on-move-property-back]}]
  {:pre [(ifn? on-move-property-back)]}
  (let [props {:button/label "/\\"
               :button.messages/on-click on-move-property-back}]
    [components.button/button props]))

(defn- move-forward-button
  [{:template-properties-form.actions-buttons.messages/keys [on-move-property-forward]}]
  {:pre [(ifn? on-move-property-forward)]}
  (let [props {:button/label "\\/"
               :button.messages/on-click on-move-property-forward}]
    [components.button/button props]))

(defn template-properties-form-actions-buttons [props]
  [:div {:class base-class}
   [remove-button props]
   [move-back-button props]
   [move-forward-button props]])

(defmethod factories.input/input-factory-opts ::template-properties-form-actions-buttons [_]
  {:factories.input/component template-properties-form-actions-buttons
   :factories.input/assoc-disabled? #(do %1)
   :factories.input/assoc-value-to-props-fn #(do %1)})
