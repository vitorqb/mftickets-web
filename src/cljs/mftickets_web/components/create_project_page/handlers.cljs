(ns mftickets-web.components.create-project-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.create-project-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.create-project-page.queries :as queries]))

(defn on-raw-project-change [{:keys [state]} new-raw-project]
  (swap! state (reducers/set-raw-project new-raw-project)))

(defn- before-create-project-submit [{:keys [state]}]
  (swap! state (comp (reducers/set-create-project-response nil)
                     (reducers/set-loading? true))))

(defn- after-create-project-submit
  [{:keys [state] :create-project-page.messages/keys [refresh-app-metadata]}
   response]
  {:pre [(ifn? refresh-app-metadata)]}
  (swap! state (comp (reducers/set-create-project-response response)
                     (reducers/set-loading? false)))
  (refresh-app-metadata))

(defn on-create-project-submit
  [{{:keys [create-project]} :http state :state :as props}]
  {:pre [(ifn? create-project)]}
  (before-create-project-submit props)
  (async/go
    [(->> @state queries/raw-project create-project async/<! (after-create-project-submit props))]))
