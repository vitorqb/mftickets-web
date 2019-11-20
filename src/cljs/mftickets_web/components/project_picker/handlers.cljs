(ns mftickets-web.components.project-picker.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as s]))

(defn on-select-change
  [{:project-picker.messages/keys [on-picked-project-change]} new-option]
  {:pre [(s/assert :select/option new-option) (s/assert ifn? on-picked-project-change)]}
  (-> new-option :value on-picked-project-change))
