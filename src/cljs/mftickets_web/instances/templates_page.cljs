(ns mftickets-web.instances.templates-page
  (:require
   [mftickets-web.components.templates-page :as components.templates-page]
   [mftickets-web.state :as state]
   [mftickets-web.app.handlers :as handlers]
   [mftickets-web.events :as events]
   [mftickets-web.components.table :as components.table]
   [mftickets-web.app.queries :as queries]))

(defn templates-page-instance
  [{:keys [app-state http]}]
  (components.templates-page/templates-page
   {:templates-page/current-project-id (:id (queries/active-project @app-state))
    :components {:table components.table/table}
    :state (state/->FocusedAtom app-state [::state])
    :http http}))
