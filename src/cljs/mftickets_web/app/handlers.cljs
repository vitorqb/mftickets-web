(ns mftickets-web.app.handlers
  (:require
   [accountant.core :as accountant]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.app.reducers :as reducers]
   [cljs.core.async :as async]))

(defn display-router-dialog [{:keys [app-state]}]
  (swap! app-state (reducers/display-router-dialog)))

(defn close-router-dialog [{:keys [app-state]}]
  (swap! app-state (reducers/close-router-dialog)))

(defn navigate [_ href]
  (accountant/navigate! href))

(defn fetch-app-metadata-response
  [{app-state :app-state {:keys [get-app-metadata]} :http}]
  {:pre [(ifn? get-app-metadata)]}
  (async/go
    (let [response (async/<! (get-app-metadata))]
      (swap! app-state (reducers/set-app-metadata-response response)))))

(defn update-token
  [{:keys [app-state] :as injections} new-token]
  (swap! app-state (reducers/set-token new-token))
  (fetch-app-metadata-response injections))

(defn with-confirmation
  "Asks the user to confirm an action and calls message on success."
  [{:keys [message prompt]}]
  (when (js/confirm prompt)
    (message)))

(defn update-current-project [{:keys [app-state]} new-project]
  (->> new-project :id reducers/set-active-project-id (swap! app-state)))
