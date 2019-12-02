(ns mftickets-web.components.dialog.handlers
  (:require
   [mftickets-web.components.dialog.reducers :as reducers]))

(defn close [{:keys [state]}]
  (swap! state (reducers/set-disabled? true)))
