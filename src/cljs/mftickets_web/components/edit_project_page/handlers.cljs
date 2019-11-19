(ns mftickets-web.components.edit-project-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.edit-project-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.edit-project-page.queries :as queries]))

(defrecord PickedProjectChange [new-picked-project]
  events.protocols/PEvent
  (reduce! [_] (reducers/new-picked-project new-picked-project)))

(defrecord EditedProjectSubmit--before []
  events.protocols/PEvent
  (reduce! [_] (comp (reducers/set-loading? true)
                     (reducers/set-edit-project-response nil))))

(defrecord EditedProjectSubmit--after [props edit-project-response]
  events.protocols/PEvent
  (reduce! [_] (comp (reducers/set-loading? false)
                     (reducers/edit-project-response edit-project-response)))
  (propagate! [_]
    (let [refresh-app-metadata-> (-> props :events :refresh-app-metadata->)]
      [(refresh-app-metadata->)])))

(defrecord EditedProjectSubmit [props]
  events.protocols/PEvent
  (dispatch! [_] [(->EditedProjectSubmit--before)])
  (run-effects! [_]
    (let [edit-project (-> props :http :edit-project)]
      (async/go
        [(->> @(:state props)
              queries/edited-project
              edit-project
              async/<!
              (->EditedProjectSubmit--after props))]))))

(defn on-project-form-edited-project-change
  "Handles messages from project form when the edited project changes."
  [{:keys [state]} new-edited-project]
  (swap! state (reducers/set-edited-project new-edited-project)))
