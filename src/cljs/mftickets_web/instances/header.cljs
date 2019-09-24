(ns mftickets-web.instances.header
  (:require
   [mftickets-web.components.header :as components.header]
   [mftickets-web.state :as state]
   [mftickets-web.state :as state]))

(defn header-instance
  [{:keys [app-state http messages]}]
  [components.header/header
   {:state   (state/->FocusedAtom app-state [::state])
    :http    http
    :messages messages}])

