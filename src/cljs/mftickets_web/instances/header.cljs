(ns mftickets-web.instances.header
  (:require
   [mftickets-web.components.header :as components.header]
   [mftickets-web.state :as state]
   [mftickets-web.events :as events]
   [mftickets-web.app.handlers :as handlers]))

(defn header-instance
  [{:keys [app-state http messages]}]
  [components.header/header
   {:state   (state/->FocusedAtom app-state [::state])
    :http    http
    :messages messages
    :events {:display-router-dialog-> handlers/display-router-dialog}
    :parent-react! #(events/react! {:state app-state} %)}])

