(ns mftickets-web.instances.login-page
  (:require
   [mftickets-web.components.login-page :as components.login-page]))

(defn- get-state [app-state] (get @app-state ::state))
(defn- mk-reduce [app-state] #(swap! app-state update ::state %))

(defn login-page-instance
  [{:keys [app-state]}]
  [components.login-page/login-page
   {:state   (get-state app-state)
    :reduce! (mk-reduce app-state)}])
