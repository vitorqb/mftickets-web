(ns mftickets-web.instances.header
  (:require
   [mftickets-web.components.header :as components.header]))

(defn- get-state [app-state] (get @app-state ::state))
(defn- mk-reduce [app-state] #(swap! app-state update ::state %))

(defn header-instance
  [{:keys [app-state http send-message!]}]
  [components.header/header
   {:state   (get-state app-state)
    :reduce! (mk-reduce app-state)
    :http    http
    :send-message! send-message!}])

