(ns mftickets-web.components.projects-page.handlers
  (:require
   [cljs.core.async :as async]
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.components.projects-page.reducers :as reducers]))

(defn fetch-projects--before
  [_]
  (reify events.protocols/PEvent
    (reduce! [_] (comp (reducers/set-loading? true)
                       (reducers/set-fetch-projects-response nil)))))

(defn fetch-projects--after
  [_ response]
  (reify events.protocols/PEvent
    (reduce! [_] (comp (reducers/set-loading? false)
                       (reducers/set-fetch-projects-response response)))))

(defn fetch-projects
  "Fetches all projects."
  [{{:keys [get-projects]} :http :as props}]

  (reify events.protocols/PEvent

    (dispatch! [_] [(fetch-projects--before props)])

    (run-effects! [_]
      (async/go [(->> (get-projects) async/<! (fetch-projects--after props))]))))

(defn init
  "Initialization handler."
  [props]
  (reify events.protocols/PEvent
    (dispatch! [_] [(fetch-projects props)])))

