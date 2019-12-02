(ns mftickets-web.instances.edit-project-page
  (:require
   [mftickets-web.components.edit-project-page :as components.edit-project-page]
   [mftickets-web.state :as state]
   [mftickets-web.app.queries :as app.queries]
   [mftickets-web.app.handlers :as app.handlers]))

(defn edit-project-page-instance
  "An instance of the page to edit a project."
  [{:keys [app-state http] :as inject}]
  [components.edit-project-page/edit-project-page
   {:edit-project-page/projects (app.queries/projects @app-state)
    :edit-project-page.messages/refresh-app-metadata
    #(app.handlers/fetch-app-metadata-response inject)
    :state (state/->FocusedAtom app-state ::state)
    :http http}])
