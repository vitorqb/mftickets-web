(ns mftickets-web.instances.header
  (:require
   [mftickets-web.components.header :as components.header]
   [mftickets-web.state :as state]))

(defn- get-state [app-state] (get @app-state ::state))
(defn- mk-reduce [app-state] #(swap! app-state update ::state %))

(defn header-instance
  [{:keys [app-state http messages]}]
  [components.header/header
   {:state   (state/->FocusedAtom app-state [::state])
    :http    http
    :messages messages}])

