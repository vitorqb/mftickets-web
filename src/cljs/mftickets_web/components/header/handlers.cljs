(ns mftickets-web.components.header.handlers
  (:require
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn display-router [{{:keys [DisplayRouterDialog->]} :events}]
  (reify events.protocols/PEvent
    (propagate! [_] [(DisplayRouterDialog->)])))

