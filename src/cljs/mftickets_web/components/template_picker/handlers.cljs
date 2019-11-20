(ns mftickets-web.components.template-picker.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as spec]))

(defn on-select-change
  [{:template-picker.messages/keys [on-template-picked]} picked-option]

  {:pre [(spec/assert fn? on-template-picked)]}

  (-> picked-option :value on-template-picked))
