(ns mftickets-web.instances.templates-page
  (:require
   [mftickets-web.components.templates-page :as components.templates-page]
   [mftickets-web.state :as state]
   [mftickets-web.app.handlers :as handlers]
   [mftickets-web.events :as events]))

(defn templates-page-instance
  [{:keys [app-state]}]
  (components.templates-page/templates-page
   {:state (state/->FocusedAtom app-state [::state])}))
