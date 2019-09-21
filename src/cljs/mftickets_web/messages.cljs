(ns mftickets-web.messages
  (:require
   [mftickets-web.instances.router-dialog :as instances.router-dialog]
   [mftickets-web.components.router-dialog :as components.router-dialog]
   [mftickets-web.components.dialog.reducers :as components.dialog.reducers]))

(defn m-update-token [token] #(assoc % :token token))

(defn m-display-router-dialog
  "Updates the app state to display the router dialog."
  []
  (fn [app-state]
    (update-in
     app-state
     [::instances.router-dialog/state ::components.router-dialog/dialog]
     (components.dialog.reducers/set-disabled? false))))

(defn messages-getter
  "Prepares a lookable object for message functions.
  `messages-fns` is a map where each value is a function returning a reducer.  
  Everytime the getter returns a value, it reduces the app-state using the result
  of calling the reducer."
  [messages-fns app-state]

  (reify
    ILookup
    (-lookup [this k]
      (-lookup this k nil))

    (-lookup [this k not-found]
      (if-let [f (get messages-fns k)]
        (fn wrapped-f [& args] (swap! app-state (apply f args)))
        not-found))))
