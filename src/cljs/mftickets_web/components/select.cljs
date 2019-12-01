(ns mftickets-web.components.select
  (:require
   [reagent.core :as r]
   ["react-select" :default Select]
   ["react-select/async" :default AsyncSelect]
   [cljs.spec.alpha :as s]
   [mftickets-web.components.select.handlers :as handlers]
   [mftickets-web.events :as events]))

(def base-class "select")
(def base-label-class "select__label")

;; Specs
(s/def :select/label
  string?)

(s/def :select/option
  (s/and (s/keys :req-un [:select/label]) #(contains? % :value)))

(s/def :select/value
  (s/nilable :select/option))

(s/def :select/options
  (s/coll-of :select/option))

(s/def :select.messages/on-select-change ifn?)

(s/def :select/label-wrapper-class
  string?)

(s/def :select/contents-wrapper-class
  string?)

(s/def :select/disabled
  boolean?)

(s/def :select/props
  (s/keys :req [:select/value :select/options :select.messages/on-select-change]
          :opt [:select/disabled]))

(s/def :select.async/get-matching-options
  fn?)

(s/def :select.async/props
  (s/keys :req [:select/value :select.async/get-matching-options :select.messages/on-select-change]
          :opt [:select/label :select/label-wrapper-class :select/contents-wrapper-class]))

;; Component
(defn- label
  "A label for the select component."
  [{:select/keys [label label-wrapper-class]}]
  (when label
    [:span {:class label-wrapper-class}
     [:span {:class base-label-class}
      label]]))

(defn select
  "A wrapper around ReactSelect."
  [{:select/keys [options value label-wrapper-class contents-wrapper-class disabled]
    :or {disabled false}
    :as props}]

  {:pre [(s/assert :select/props props)]}

  [:div {:class base-class}
   [label props]
   [:div {:class contents-wrapper-class}
    [(r/adapt-react-class Select)
     {:value value
      :options options
      :on-change #(handlers/on-change props %)
      :isDisabled disabled}]]])

(defn async-select
  "A wrapper around ReactSelect Async."
  [{:keys [events]
    :select/keys [value]
    :as props}]

  {:pre [(s/assert :select.async/props props)]}

  [(r/adapt-react-class AsyncSelect)
   {:value value
    :load-options #(handlers/on-load-options props %1 %2)
    :on-change #(handlers/on-change props %)}])
