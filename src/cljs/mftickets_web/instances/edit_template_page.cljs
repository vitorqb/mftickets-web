(ns mftickets-web.instances.edit-template-page
  (:require [mftickets-web.components.edit-template-page :as components.edit-template-page]
            [mftickets-web.state :as state]
            [cljs.core.async :as async]
            [mftickets-web.app.queries :as app.queries]))

(defn edit-template-page-instance
  [{:keys [app-state http]}]
  (let [project (app.queries/active-project @app-state)
        property-types (app.queries/properties-types @app-state)]
    [components.edit-template-page/edit-template-page
     {:state (state/->FocusedAtom app-state ::state)
      :http http
      :edit-template-page/project-id (:id project)
      :edit-template-page/property-types property-types}]))

