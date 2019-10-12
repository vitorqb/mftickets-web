(ns mftickets-web.components.router-dialog.handlers
  (:require
   [mftickets-web.events.protocols :as events.protocols]))

(defrecord CloseRouterDialog [props]
  events.protocols/PEvent
  (propagate! [_]
    (let [CloseRouterDialog-> (-> props :events :CloseRouterDialog->)
          _ (assert (fn? CloseRouterDialog->))]
      [(CloseRouterDialog->)])))

(defrecord Navigate [props href]
  events.protocols/PEvent
  (propagate! [_]
    (let [Navigate-> (-> props :events :Navigate->)]
      [(Navigate-> href)])))

