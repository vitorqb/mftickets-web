(ns mftickets-web.components.select
  (:require
   [reagent.core :as r]
   ["react-select" :default Select]
   ["react-select/async" :default AsyncSelect]
   [cljs.spec.alpha :as s]
   [mftickets-web.components.select.handlers :as handlers]
   [mftickets-web.events :as events]
   [mftickets-web.events.specs :as events.specs]))

;; Specs
(s/def :select/label
  string?)

(s/def :select/option
  (s/and (s/keys :req-un [:select/label]) #(contains? % :value)))

(s/def :select/value
  (s/nilable :select/option))

(s/def :select/options
  (s/coll-of :select/option))

(s/def :select.events/Change->
  fn?)

(s/def :select/events
  (s/keys :req-un [:select.events/Change->]))

(s/def :select/props
  (s/keys :req-un [::events.specs/parent-props :select/events]
          :req [:select/value :select/options]))

(s/def :select.async/get-matching-options
  fn?)

(s/def :select.async/props
  (s/keys :req-un [::events.specs/parent-props :select/events]
          :req [:select/value :select.async/get-matching-options]))

;; Component
(defn select
  "A wrapper around ReactSelect."
  [{:keys [events]
    :select/keys [options value]
    :as props}]

  {:pre [(s/assert :select/props props)]}

  [(r/adapt-react-class Select)
   {:value value
    :options options
    :on-change #(->> % (handlers/->Change props) (events/react! props))}])

(defn async-select
  "A wrapper around ReactSelect Async."
  [{:keys [events]
    :select/keys [value]
    :as props}]

  {:pre [(s/assert :select.async/props props)]}

  [(r/adapt-react-class AsyncSelect)
   {:value value
    :load-options #(->> %& (apply handlers/->OnLoadOptions props) (events/react! props))
    :on-change #(->> % (handlers/->Change props) (events/react! props))}])
