(ns mftickets-web.components.dialog.handlers
  (:require
   [mftickets-web.components.dialog.reducers :as reducers]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn close [{:keys [state]}]
  (swap! state (reducers/set-disabled? true)))
