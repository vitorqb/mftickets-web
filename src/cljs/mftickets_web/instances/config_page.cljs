(ns mftickets-web.instances.config-page
  (:require [mftickets-web.components.config-page :as components.config-page]
            [mftickets-web.app.queries :as queries]
            [mftickets-web.app.handlers :as handlers]))

(defn config-page-instance
  "An instance of config page."
  [{:keys [app-state] :as inject}]
  [components.config-page/config-page
   {:config-page/projects (queries/projects @app-state)
    :config-page/active-project (queries/active-project @app-state)
    :config-page.messages/update-current-project #(handlers/update-current-project inject %)
    :parent-props {:state app-state}}])
