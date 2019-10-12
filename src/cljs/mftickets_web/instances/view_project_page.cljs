(ns mftickets-web.instances.view-project-page
  (:require
   [mftickets-web.components.view-project-page :as components.view-project-page]
   [mftickets-web.app.queries :as queries]
   [mftickets-web.state :as state]
   [mftickets-web.app.handlers :as app.handlers]))

(defn view-project-page-instance
  "Instance of a page component to view a project."
  [{:keys [app-state http] :as injections}]
  (let [projects (queries/projects @app-state)
        state (state/->FocusedAtom app-state [::state])
        refresh-app-metadata #(app.handlers/->FetchAppMetadataResponse {:http http})]
    [components.view-project-page/view-project-page
     {:view-project-page/projects projects
      :state state
      :events {:WithConfirmation-> app.handlers/->WithConfirmation
               :refresh-app-metadata refresh-app-metadata}
      :parent-props {:state app-state}
      :http http}]))
