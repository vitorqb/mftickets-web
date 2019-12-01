(ns mftickets-web.instances.create-project-page
  (:require [mftickets-web.components.create-project-page :as components.create-project-page]
            [mftickets-web.state :as state]
            [mftickets-web.app.handlers :as app.handlers]))

(defn create-project-page-instance
  "An instace of the page to create a new project."
  [{:keys [app-state http] :as inject}]
  [components.create-project-page/create-project-page
   {:create-project-page.messages/refresh-app-metadata
    #(app.handlers/fetch-app-metadata-response inject)
    :state (state/->FocusedAtom app-state [::state])
    :http http}])
