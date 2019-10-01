(ns mftickets-web.app.handlers
  (:require
   [accountant.core :as accountant]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.app.reducers :as reducers]
   [cljs.core.async :as async]))

(defn display-router-dialog
  "An event to display the router dialog."
  []
  ^{::name "display-router-dialog"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/display-router-dialog))))

(defn close-router-dialog
  "An events to hide the router dialog."
  []
  ^{::name "close-router-dialog"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/close-router-dialog))))

(defn navigate
  "An event to navigate to another page."
  [href]
  ^{::name "navigate"}
  (reify events.protocols/PEvent
    (run-effects! [_] (do (accountant/navigate! href) nil))))

(defn fetch-app-metadata-response--after
  "An event that fetches global app metadata."
  [app-metadata-response]
  ^{::name "fetch-app-metadata-response--after"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-app-metadata-response app-metadata-response))))

(defn fetch-app-metadata-response
  "Fetches the metadata for the app."
  [{{:keys [get-app-metadata]} :http}]
  ^{::name "fetch-app-metadata-response"}
  (reify events.protocols/PEvent
    (run-effects! [_]
      (async/go [(-> (get-app-metadata) async/<! fetch-app-metadata-response--after)]))))

(defn update-token
  "An event to update the current token."
  [props new-token]

  ^{::name "update-token"}
  (reify events.protocols/PEvent

    ;; Reduce app state to set the new token
    (reduce! [_] (reducers/set-token new-token))

    ;; Refresh the app metadata
    (dispatch! [_] [(fetch-app-metadata-response props)])))
