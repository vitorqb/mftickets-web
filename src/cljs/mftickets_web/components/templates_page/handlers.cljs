(ns mftickets-web.components.templates-page.handlers
  (:require
   [mftickets-web.components.templates-page.reducers :as reducers]
   [cljs.core.async :as async]))

(defn after-fetch-templates [{:keys [state]} response]
  (swap! state (reducers/set-templates-http-response response)))

(defn on-fetch-templates
  [{{:keys [get-templates]} :http :templates-page/keys [current-project-id] :as props}]
  (async/go (->> {:project-id current-project-id}
                 get-templates
                 async/<!
                 (after-fetch-templates props))))

(defn init [props] (on-fetch-templates props))
