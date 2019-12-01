(ns mftickets-web.instances.login-page
  (:require
   [mftickets-web.components.login-page :as components.login-page]
   [mftickets-web.state :as state]
   [mftickets-web.app.handlers :as handlers]
   [mftickets-web.events :as events]))

(defn login-page-instance
  [{:keys [app-state http] :as inject}]
  [components.login-page/login-page
   {:login-page.messages/update-token #(handlers/update-token inject %)
    :state         (state/->FocusedAtom app-state [::state])
    :http          http}])
