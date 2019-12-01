(ns mftickets-web.components.edit-project-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.edit-project-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.edit-project-page.queries :as queries]))

(defn on-picked-project-change [{:keys [state]} new-picked-project]
  (swap! state (reducers/new-picked-project new-picked-project)))

(defn- before-edited-project-submit
  [{:keys [state]}]
  {:pre [(or (satisfies? IAtom state) (satisfies? ISwap state))]}
  (swap! state (comp (reducers/set-loading? true) (reducers/set-edit-project-response nil))))

(defn- after-edited-project-submit
  [{:keys [state]
    :edit-project-page.messages/keys [refresh-app-metadata]
    {:keys [edit-project]} :http}
   response]

  {:pre [(or (satisfies? IAtom state) (satisfies? ISwap state))
         (ifn? refresh-app-metadata)]}
  
  (swap! state (comp (reducers/set-loading? false) (reducers/edit-project-response response)))
  (refresh-app-metadata))

(defn on-edited-project-submit
  [{:keys [state]
    {:keys [edit-project]} :http
    :as props}]
  {:pre [(ifn? edit-project)]}
  (before-edited-project-submit props)
  (async/go
    (->> @state queries/edited-project edit-project (after-edited-project-submit props))))

(defn on-project-form-edited-project-change
  "Handles messages from project form when the edited project changes."
  [{:keys [state]} new-edited-project]
  (swap! state (reducers/set-edited-project new-edited-project)))
