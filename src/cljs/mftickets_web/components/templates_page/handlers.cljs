(ns mftickets-web.components.templates-page.handlers
  (:require
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.components.templates-page.reducers :as reducers]
   [cljs.core.async :as async]))

(defn fetch-templates--after
  [response]
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-templates-http-response response))))

(defn fetch-templates
  "Fetches the templates list."
  [{{:keys [get-templates]} :http :templates-page/keys [current-project-id]}]
  (reify events.protocols/PEvent
    (run-effects! [_]
      (async/go [(-> {:project-id current-project-id}
                     get-templates
                     async/<!
                     fetch-templates--after)]))))

(defn init!
  "Initialization event."
  [props]
  (reify events.protocols/PEvent
    (dispatch! [_] [(fetch-templates props)])))
