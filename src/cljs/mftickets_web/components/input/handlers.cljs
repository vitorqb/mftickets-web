(ns mftickets-web.components.input.handlers
  (:require
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn on-change
  "An event for an input value change."
  [{{:keys [on-change->]} :events} e]
  (reify events.protocols/PEvent
    (propagate! [_] (when on-change-> [(-> e .-target .-value on-change->)]))))

(defn on-key-up
  "An event for a key up."
  [{{:keys [on-key-up->]} :events} e]
  (reify events.protocols/PEvent
    (propagate! [_] (when on-key-up-> [(-> e .-key on-key-up->)]))))
