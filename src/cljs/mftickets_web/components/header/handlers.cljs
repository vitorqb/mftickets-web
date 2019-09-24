(ns mftickets-web.components.header.handlers
  (:require
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn display-router [{{:keys [display-router-dialog]} :messages}]
  (reify events.protocols/PEvent
    (run-effects! [_] (do (display-router-dialog) nil))))

