(ns mftickets-web.components.router-dialog.handlers
  (:require
   [mftickets-web.events.protocols :as events.protocols]))

(defn close-router-dialog->
  [{{:keys [CloseRouterDialog->]} :events}]
  (reify events.protocols/PEvent
    (propagate! [_] [(CloseRouterDialog->)])))

(defn navigate->
  [{{:keys [Navigate->]} :events} href]
  (reify events.protocols/PEvent
    (propagate! [_] [(Navigate-> href)])))

