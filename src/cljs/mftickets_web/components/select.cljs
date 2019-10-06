(ns mftickets-web.components.select
  (:require
   [reagent.core :as r]
   ["react-select" :default Select]
   [cljs.spec.alpha :as s]
   [mftickets-web.components.select.handlers :as handlers]
   [mftickets-web.events :as events]))

;; Specs
(s/def :select/label string?)
(s/def :select/option (s/and (s/keys :req-un [:select/label])
                       #(contains? % :value)))
(s/def :select/value (s/nilable :select/option))
(s/def :select/options (s/coll-of :select/option))
(s/def :select/events #(-> % :on-change-> fn?))

;; Component
(defn select
  "A wrapper around ReactSelect."
  [{:keys [events]
    :select/keys [options value]
    :as props}]

  {:pre [(s/valid? :select/options options)
         (s/valid? :select/value value)
         (s/valid? :select/events events)]}

  [(r/adapt-react-class Select)
   {:value value
    :options options
    :on-change #(->> % (handlers/on-change props) (events/react! props))}])
