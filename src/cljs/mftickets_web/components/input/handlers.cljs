(ns mftickets-web.components.input.handlers
  (:require
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

;; !!!! TODO -> REMOVE
(defrecord OnChange [props event]
  events.protocols/PEvent
  (propagate! [_]
    (if-let [OnChange-> (-> props :events :OnChange->)]
      [(-> event .-target .-value OnChange->)])))

(defrecord OnKeyUp [props event]
  events.protocols/PEvent
  (propagate! [_]
    (if-let [OnKeyUp-> (-> props :events :OnKeyUp->)]
      [(-> event .-key OnKeyUp->)])))

(defn on-html-input-change
  [{:input.messages/keys [on-change]} event]
  {:pre [(fn? on-change)]}
  (-> event .-target .-value on-change))
