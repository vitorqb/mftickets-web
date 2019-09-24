(ns mftickets-web.components.dialog.handlers
  (:require
   [mftickets-web.components.dialog.reducers :as reducers]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn close
  "Event for closing the router dialog."
  []
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-disabled? true))))
