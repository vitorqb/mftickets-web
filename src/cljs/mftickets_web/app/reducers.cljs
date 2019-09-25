(ns mftickets-web.app.reducers
  (:require
   [com.rpl.specter :as s]
   [mftickets-web.components.router-dialog :as components.router-dialog]
   [mftickets-web.components.dialog.reducers :as components.dialog.reducers]))

(defn display-router-dialog
  "Updates the app state to display the router dialog."
  []
  (fn [state]
    (s/transform
     [:mftickets-web.instances.router-dialog/state ::components.router-dialog/dialog]
     (components.dialog.reducers/set-disabled? false)
     state)))

(defn close-router-dialog
  "Updates the app to hide the router dialog."
  []
  (fn i-close-router-dialog [state]
    (s/transform
     [:mftickets-web.instances.router-dialog/state ::components.router-dialog/dialog]
     (components.dialog.reducers/set-disabled? true)
     state)))

(defn set-token
  "Set's the current token."
  [new-token]
  #(assoc % :token new-token))