(ns mftickets-web.instances.view-project-page
  (:require
   [mftickets-web.components.view-project-page :as components.view-project-page]
   [mftickets-web.app.queries :as queries]
   [mftickets-web.state :as state]
   [mftickets-web.app.handlers :as app.handlers]))

(defn view-project-page-instance
  "Instance of a page component to view a project."
  [{:keys [app-state http] :as injections}]
  [components.view-project-page/view-project-page
   {:view-project-page/projects (queries/projects @app-state)
    :view-project-page.messages/with-confirmation #(app.handlers/with-confirmation %)
    :view-project-page.messages/refresh-app-metadata
    #(app.handlers/fetch-app-metadata-response injections)
    :state (state/->FocusedAtom app-state [::state])
    :parent-props {:state app-state}
    :http http}])
