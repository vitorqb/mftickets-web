(ns mftickets-web.components.input)

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
  []
  [:input {:class base-html-input-class}])

(defn input
  "Wrapper for an input component."
  [{:keys [label]}]
  [:div {:class base-input-wrapper-class}
   [label-span label]
   [html-input]])
