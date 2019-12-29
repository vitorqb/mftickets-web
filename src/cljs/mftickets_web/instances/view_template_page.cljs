(ns mftickets-web.instances.view-template-page
  (:require [mftickets-web.components.view-template-page :as components.view-template-page]
            [mftickets-web.state :as state]
            [cljs.core.async :as async]
            [mftickets-web.app.queries :as app.queries]
            [mftickets-web.app.handlers :as app.handlers]))

(defn view-template-page-instance
  [{:keys [app-state http]}]
  (let [app-state* @app-state
        project (app.queries/active-project app-state*)
        properties-types (app.queries/properties-types app-state*)]
    [components.view-template-page/view-template-page
     {:state (state/->FocusedAtom app-state ::state)
      :http http
      :view-template-page/project-id (:id project)
      :view-template-page/properties-types properties-types
      :view-template-page.messages/with-confirmation app.handlers/with-confirmation}]))
