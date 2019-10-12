(ns mftickets-web.components.header.handlers
  (:require
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defrecord DisplayRouter [props]
  events.protocols/PEvent
  (propagate! [_]
    (let [DisplayRouterDialog-> (-> props :events :DisplayRouterDialog->)]
      [(DisplayRouterDialog->)])))

