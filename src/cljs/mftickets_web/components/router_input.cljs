(ns mftickets-web.components.router-input
  (:require
   [mftickets-web.domain.matcher :as domain.matcher]
   [mftickets-web.domain.selector :as domain.selector]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.router-input.queries :as queries]
   [mftickets-web.components.router-input.reducers :as reducers]
   [mftickets-web.components.router-input.handlers :as handlers]
   [mftickets-web.events :as events]))

(def base-class "router-input")
(def options-list-class (str base-class "__options-list"))
(def option-base-class (str base-class "__option"))
(def option-selected-modifier (str option-base-class "--selected"))

(defn- matching-options
  "Returns the options matched by a user inputted string."
  [options s]
  (domain.matcher/matching-options options s {:get-str-fn :label}))

(defn- get-selected-el-index
  "Returns the index for the currently selected option."
  [{:router-input/keys [matching-options] :keys [state]}]
  (->> @state queries/selection-history (domain.selector/get-selected-el-index matching-options)))

(defn- get-matching-options
  "Returns the matching options given the current value."
  [{:router-input/keys [options] :keys [state]}]
  (->> @state queries/input-value (matching-options options)))

(defn- get-selected-option
  "Returns the selected option given the current state."
  [{:router-input/keys [matching-options selected-el-index]}]
  (get (vec matching-options) selected-el-index))

(defn- extend-props
  "Returns the props with the added values."
  [props]
  (as-> props it
      (assoc it :router-input/matching-options (get-matching-options it))
      (assoc it :router-input/selected-el-index (get-selected-el-index it))
      (assoc it :router-input/selected-option (get-selected-option it))))

(defn- input
  "A wrapper around an input, where the user types to select a route."
  [{:keys [state] :as props}]
  [components.input/input
   {:input/value (queries/input-value @state)
    :input/autofocus true
    :input.messages/on-change #(handlers/on-input-change props %)
    :events {:OnKeyUp-> #(->> % (handlers/->InputKeyUp props) (events/react! props))}
    :parent-props props}])

(defn- option-el
  "An element representing an option inside an options list."
  [{{:keys [label href]} ::option ::keys [selected?]}]
  (let [class (cond-> [option-base-class] selected? (conj option-selected-modifier))]
    [:div {:class class} [:a {:href href} label]]))

(defn- options-list
  "A div with a list of options that matches the current input."
  [{:router-input/keys [matching-options selected-el-index] :as props}]
  [:div {:class options-list-class}
   (for [[option i] (map vector matching-options (range))
         :let [props {::selected? (= i selected-el-index) ::option option}]]
     ^{:key option} [option-el props])])

(defn router-input
  "An input with autocompletion for the router."
  [props]
  (let [props* (extend-props props)]
    [:div {:class base-class}
     [input props*]
     [options-list props*]]))
