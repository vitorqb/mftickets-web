(ns mftickets-web.app.handlers
  (:require
   [accountant.core :as accountant]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.app.reducers :as reducers]
   [cljs.core.async :as async]))

(defrecord DisplayRouterDialog []
  events.protocols/PEvent
  (reduce! [_] (reducers/display-router-dialog)))

(defrecord CloseRouterDialog []
  events.protocols/PEvent
  (reduce! [_] (reducers/close-router-dialog)))

(defrecord Navigate [href]
  events.protocols/PEvent
  (run-effects! [_] (do (accountant/navigate! href) nil)))

(defrecord FetchAppMetadataResponse--after [app-metadata-response]
  events.protocols/PEvent
  (reduce! [_] (reducers/set-app-metadata-response app-metadata-response)))

(defrecord FetchAppMetadataResponse [props]
  events.protocols/PEvent
  (run-effects! [_]
    (let [get-app-metadata (-> props :http :get-app-metadata)]
      (async/go [(-> (get-app-metadata) async/<! ->FetchAppMetadataResponse--after)]))))

(defrecord UpdateToken [props new-token]
  events.protocols/PEvent

  ;; Reduce app state to set the new token
  (reduce! [_] (reducers/set-token new-token))

  ;; Refresh the app metadata
  (dispatch! [_] [(->FetchAppMetadataResponse props)]))

(defrecord WithConfirmation [opts]
  ;; Returns an event that asks the user for confirmation and only dispatches the next
  ;; event if it confirms.
  events.protocols/PEvent
  (run-effects! [_]
    (let [{:keys [prompt props event]} opts]
      (when (js/confirm prompt)
        (events/react! props event)
        nil))))
