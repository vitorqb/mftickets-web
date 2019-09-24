(ns mftickets-web.instances.login-page
  (:require
   [mftickets-web.components.login-page :as components.login-page]
   [mftickets-web.state :as state]))

(defn login-page-instance
  [{:keys [app-state http messages]}]
  [components.login-page/login-page
   {:state   (state/->FocusedAtom app-state [::state])
    :http    http
    :messages messages}])
