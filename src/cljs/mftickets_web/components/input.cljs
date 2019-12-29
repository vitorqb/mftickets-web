(ns mftickets-web.components.input
  (:require
   [mftickets-web.components.input.handlers :as handlers]
   [reagent.core :as r]
   [cljs.spec.alpha :as spec]
   [mftickets-web.components.factories.input :as factories.input]))

(def base-input-wrapper-class "input-wrapper")
(def base-html-input-class "input-wrapper__input")
(def base-input-wrapper-label-class "input-wrapper__label")

(defn label-span
  "A span with the label, if any."
  [label]
  (when label
    [:span {:class base-input-wrapper-label-class}
     label]))

(defn html-input
  "An input html component."
  [{:input/keys [value disabled id on-key-down autofocus]
    :as props}]
  (r/create-class
   {:component-did-update
    #(when autofocus (.focus (r/dom-node %)))
    
    :reagent-render
    (fn [{:input/keys [value disabled id on-key-down] :as props}]
      [:input
       {:class base-html-input-class
        :on-change #(handlers/on-html-input-change props %)
        :on-key-down #(handlers/on-key-down props %)
        :value (or value "")
        :disabled (or disabled false)}])}))

(defn input
  "Wrapper for an input component."
  [{:input/keys [label] :as props}]
  [:div {:class base-input-wrapper-class}
   [label-span label]
   [html-input props]])

(defmethod factories.input/input-factory-opts ::input [_]
  {:factories.input/component input
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :factories.input/assoc-disabled? #(assoc %1 :input/disabled %2)})
