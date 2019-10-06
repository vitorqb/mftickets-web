(ns mftickets-web.instances.edit-project-page
  (:require
   [mftickets-web.components.edit-project-page :as components.edit-project-page]
   [mftickets-web.state :as state]
   [mftickets-web.app.queries :as app.queries]
   [mftickets-web.app.handlers :as app.handlers]))

(defn edit-project-page-instance
  "An instance of the page to edit a project."
  [{:keys [app-state http]}]
  (let [refresh-app-metadata #(app.handlers/fetch-app-metadata-response {:http http})]
    [components.edit-project-page/edit-project-page
     {:state (state/->FocusedAtom app-state ::state)
      :edit-project-page/projects (app.queries/projects @app-state)
      :http http
      :events {:refresh-app-metadata-> refresh-app-metadata}
      :parent-props {:state app-state}}]))
