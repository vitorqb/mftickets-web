(ns mftickets-web.components.input
  (:require
   [mftickets-web.components.input.handlers :as handlers]
   [mftickets-web.events :as events]))

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
  [{:keys [value disabled id on-key-up] :as props}]
  [:input
   {:class base-html-input-class
    :on-change #(->> % (handlers/on-change props) (events/react! props))
    :on-key-up #(->> % (handlers/on-key-up props) (events/react! props))
    :value (or value "")
    :disabled (or disabled false)}])

(defn input
  "Wrapper for an input component."
  [{:keys [label] :as props}]
  [:div {:class base-input-wrapper-class}
   [label-span label]
   [html-input props]])
