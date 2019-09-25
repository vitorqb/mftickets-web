(ns mftickets-web.components.router-dialog.handlers
  (:require
   [mftickets-web.events.protocols :as events.protocols]))

(defn close-router-dialog->
  [{{:keys [close-router-dialog->]} :events}]
  (reify events.protocols/PEvent
    (propagate! [_] [(close-router-dialog->)])))

(defn navigate->
  [{{:keys [navigate->]} :events} href]
  (reify events.protocols/PEvent
    (propagate! [_] [(navigate-> href)])))

