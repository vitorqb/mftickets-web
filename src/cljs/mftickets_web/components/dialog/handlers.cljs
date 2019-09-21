(ns mftickets-web.components.dialog.handlers
  (:require
   [mftickets-web.components.dialog.reducers :as reducers]))

(defn close
  "Handler for closing the router dialog."
  [{:keys [reduce!]}]
  (fn [] (reduce! (reducers/set-disabled? true))))
