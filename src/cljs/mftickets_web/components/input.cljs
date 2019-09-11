(ns mftickets-web.components.input)

(def base-input-wrapper-class "input-wrapper")
(def base-html-input-class "input-wrapper__input")
(def base-input-wrapper-label-class "input-wrapper__label")

(defn- on-change-handler
  "Returns a handler for a change on the input value."
  [{:keys [on-change]}]
  (fn [event]
    (when on-change
      (-> event .-target .-value on-change))))

(defn label-span
  "A span with the label, if any."
  [label]
  (when label
    [:span {:class base-input-wrapper-label-class}
     label]))

(defn html-input
  "An input html component."
  [{:keys [value] :as props}]
  [:input
   {:class base-html-input-class
    :on-change (on-change-handler props)
    :value (or value "")}])

(defn input
  "Wrapper for an input component."
  [{:keys [label] :as props}]
  [:div {:class base-input-wrapper-class}
   [label-span label]
   [html-input props]])