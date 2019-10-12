(ns mftickets-web.components.create-project-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.create-project-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.create-project-page.queries :as queries]))

(defrecord RawProjectChange [new-raw-project]
  events.protocols/PEvent
  (reduce! [_] (reducers/set-raw-project new-raw-project)))

(defrecord CreateProjectSubmit--before []
  events.protocols/PEvent
  (reduce! [_] (comp (reducers/set-create-project-response nil)
                     (reducers/set-loading? true))))

(defrecord CreateProjectSubmit--after [props created-project-response]
  events.protocols/PEvent
  (reduce! [_] (comp (reducers/set-create-project-response created-project-response)
                     (reducers/set-loading? false)))
  (propagate! [_]
    (let [refresh-app-metadata-> (-> props :events :refresh-app-metadata->)]
      [(refresh-app-metadata->)])))

(defrecord CreateProjectSubmit [props]
  events.protocols/PEvent
  (dispatch! [_] [(->CreateProjectSubmit--before)])
  (run-effects! [_]
    (let [create-project (-> props :http :create-project)]
      (async/go
        [(->> @(:state props)
              queries/raw-project
              create-project
              async/<!
              (->CreateProjectSubmit--after props))]))))
