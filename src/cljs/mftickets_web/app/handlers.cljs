(ns mftickets-web.app.handlers
  (:require
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.app.reducers :as reducers]))

(defn display-router-dialog
  "An event to display the router dialog."
  []
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/display-router-dialog))))
