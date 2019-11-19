(ns mftickets-web.components.input.handlers
  (:require
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn on-key-up
  [{:input.messages/keys [on-key-up]} event]
  {:pre [(or (nil? on-key-up) (fn? on-key-up))]}
  (when on-key-up (-> event .-key on-key-up)))

(defn on-html-input-change
  [{:input.messages/keys [on-change]} event]
  {:pre [(fn? on-change)]}
  (-> event .-target .-value on-change))
