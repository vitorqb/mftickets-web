(ns mftickets-web.components.template-form.sections-actions-buttons
  (:require [mftickets-web.components.button :as components.button]
            [mftickets-web.components.factories.input :as factories.input]))

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

(defmethod factories.input/input-factory-opts ::template-form-sections-actions-buttons [_]
  {:factories.input/component template-form-sections-actions-buttons
   :factories.input/assoc-disabled? #(do %1)
   :factories.input/assoc-value-to-props-fn #(do %1)})
