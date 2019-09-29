(ns mftickets-web.instances.projects-page
  (:require
   [mftickets-web.components.projects-page :as components.projects-page]
   [mftickets-web.state :as state]))

(defn projects-page-instance
  "An instance of the projects page component."
  [{:keys [app-state http]}]
  [components.projects-page/projects-page
   {:state (state/->FocusedAtom app-state [::state])
    :http http}])

