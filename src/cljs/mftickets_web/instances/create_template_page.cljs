(ns mftickets-web.instances.create-template-page
  (:require [mftickets-web.components.create-template-page :as components.create-template-page]
            [mftickets-web.state :as state]
            [mftickets-web.app.queries :as queries]))

(defn create-template-page-instance [{:keys [app-state http]}]
  [components.create-template-page/create-template-page
   {:create-template-page/current-project (queries/active-project @app-state)
    :state (state/->FocusedAtom app-state [::state])
    :http http}])
