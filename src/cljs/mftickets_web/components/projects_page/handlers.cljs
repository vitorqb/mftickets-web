(ns mftickets-web.components.projects-page.handlers
  (:require
   [cljs.core.async :as async]
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.components.projects-page.reducers :as reducers]))

(defrecord FetchProjects--before []
  events.protocols/PEvent
  (reduce! [_] (comp (reducers/set-loading? true)
                     (reducers/set-fetch-projects-response nil))))

(defrecord FetchProjects--after [response]
  events.protocols/PEvent
  (reduce! [_] (comp (reducers/set-loading? false)
                     (reducers/set-fetch-projects-response response))))

(defrecord FetchProjects [props]
  events.protocols/PEvent
  (dispatch! [_] [(->FetchProjects--before)])
  (run-effects! [_]
    (let [get-projects (-> props :http :get-projects)
          _ (assert (fn? get-projects))]
      (async/go [(->> (get-projects) async/<! ->FetchProjects--after)]))))

(defrecord Init [props]
  events.protocols/PEvent
  (dispatch! [_] [(->FetchProjects props)]))

