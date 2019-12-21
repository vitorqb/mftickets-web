(ns mftickets-web.instances.create-template-page
  (:require [mftickets-web.components.create-template-page :as components.create-template-page]
            [mftickets-web.state :as state]
            [mftickets-web.app.queries :as queries]))

(defn create-template-page-instance [{:keys [app-state http]}]
  [components.create-template-page/create-template-page
   (let [app-state* @app-state
         current-project (queries/active-project app-state*)
         properties-types (queries/properties-types app-state*)]
     {:create-template-page/current-project current-project
      :create-template-page/properties-types properties-types
      :state (state/->FocusedAtom app-state [::state])
      :http http})])
