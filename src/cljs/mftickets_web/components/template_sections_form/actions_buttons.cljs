(ns mftickets-web.components.template-sections-form.actions-buttons
  (:require [mftickets-web.components.button :as components.button]
            [mftickets-web.components.factories.input :as factories.input]))

;; Scss
(def base-class "template-section-form-action-buttons")

;; Globals
(def ^:private remove-btn-label "Remove")
(def ^:private remove-btn-style :button/danger)
(def ^:private add-property-btn-label "Add Property")
(def ^:private add-property-btn-style nil)
(def ^:private move-section-back-btn-label "/\\")
(def ^:private move-section-back-btn-style nil)
(def ^:private move-section-forward-btn-label "\\/")
(def ^:private move-section-forward-btn-style nil)

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

(defn- move-section-back-button
  [{:template-sections-form.action-buttons.messages/keys [on-move-section-back]}]

  {:pre [(ifn? on-move-section-back)]}
  
  (let [props {:button/label move-section-back-btn-label
               :button/style move-section-back-btn-style
               :button.messages/on-click on-move-section-back}]
    [components.button/button props]))

(defn- move-section-forward-button
  [{:template-sections-form.action-buttons.messages/keys [on-move-section-forward]}]

  {:pre [(ifn? on-move-section-forward)]}
  
  (let [props {:button/label move-section-forward-btn-label
               :button/style move-section-forward-btn-style
               :button.messages/on-click on-move-section-forward}]
    [components.button/button props]))

(defn template-section-form-action-buttons
  [props]
  [:div {:class base-class}
   [remove-button props]
   [move-section-back-button props]
   [move-section-forward-button props]
   [add-property-button props]])

(defmethod factories.input/input-factory-opts ::template-section-form-action-buttons [_]
  {:factories.input/component template-section-form-action-buttons
   :factories.input/assoc-disabled? #(do %1)
   :factories.input/assoc-value-to-props-fn #(do %1)})
