(ns mftickets-web.instances.view-template-page
  (:require [mftickets-web.components.view-template-page :as components.view-template-page]
            [mftickets-web.state :as state]
            [cljs.core.async :as async]
            [mftickets-web.app.queries :as app.queries]))

(defn view-template-page-instance
  [{:keys [app-state http]}]
  (let [project (app.queries/active-project @app-state)]
    [components.view-template-page/view-template-page
     {:state (state/->FocusedAtom app-state ::state)
      :http http
      :view-template-page/project-id (:id project)}]))
