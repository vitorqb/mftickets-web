(ns mftickets-web.components.dialog.handlers
  (:require
   [mftickets-web.components.dialog.reducers :as reducers]))

(defn close
  "Handler for closing the router dialog."
  [{:keys [state]}]
  (fn [] (swap! state (reducers/set-disabled? true))))
