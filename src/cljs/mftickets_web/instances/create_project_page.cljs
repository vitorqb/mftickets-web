(ns mftickets-web.instances.create-project-page
  (:require [mftickets-web.components.create-project-page :as components.create-project-page]
            [mftickets-web.state :as state]
            [mftickets-web.app.handlers :as app.handlers]))

(defn create-project-page-instance
  "An instace of the page to create a new project."
  [{:keys [app-state http]}]
  (let [refresh-app-metadata #(app.handlers/fetch-app-metadata-response {:http http})]
    [components.create-project-page/create-project-page
     {:state (state/->FocusedAtom app-state [::state])
      :http http
      :events {:refresh-app-metadata-> refresh-app-metadata}
      :parent-props {:state app-state}}]))
