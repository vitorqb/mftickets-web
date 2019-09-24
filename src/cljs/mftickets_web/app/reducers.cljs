(ns mftickets-web.app.reducers
  (:require
   [com.rpl.specter :as s]
   [mftickets-web.instances.router-dialog :as instances.router-dialog]
   [mftickets-web.components.router-dialog :as components.router-dialog]
   [mftickets-web.components.dialog.reducers :as components.dialog.reducers]))

(defn display-router-dialog
  "Updates the app state to display the router dialog."
  []
  (fn [state]
    (s/transform
     [::instances.router-dialog/state ::components.router-dialog/dialog]
     (components.dialog.reducers/set-disabled? false)
     state)))
