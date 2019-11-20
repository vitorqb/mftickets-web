(ns mftickets-web.components.view-project-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.view-project-page.reducers :as reducers]
            [cljs.spec.alpha :as spec]
            [mftickets-web.components.view-project-page.queries :as queries]
            [cljs.core.async :as async]))

;; Helpers
(defn- on-delete-picked-project-prompt
  "The prompt used to ask the user for deletion of a picked project."
  [{:keys [name]}]
  {:pre [(spec/valid? string? name)]
   :post [(spec/valid? string? %)]}
  (str "Are you sure you want to delete: " name "?"))

;; Events
(defn on-picked-project-change [{:keys [state]} picked-project]
  (swap! state (reducers/set-picked-project picked-project)))

(defrecord DeltePickedProject--perform--after [props response]
  events.protocols/PEvent
  (reduce! [_] (reducers/after-delete-picked-project response))
  (propagate! [_]
    (let [{{:keys [refresh-app-metadata]} :events} props
          _ (assert (fn? refresh-app-metadata))]
      [(refresh-app-metadata)])))

(defrecord DeltePickedProject--perform [props project]
  events.protocols/PEvent
  (run-effects! [_]
    (let [{{:keys [delete-project]} :http} props]
      (async/go
        [(->> project
              delete-project
              async/<!
              (->DeltePickedProject--perform--after props))]))))

(defrecord DeletePickedProject [props]
  events.protocols/PEvent
  (propagate! [_]
    (let [{{:keys [WithConfirmation->]} :events :keys [state]} props
          picked-project (queries/picked-project @state)
          perform-event (->DeltePickedProject--perform props picked-project)
          confirmation-prompt (on-delete-picked-project-prompt picked-project)
          WithConfirmation-opts {:props props
                                 :event perform-event
                                 :prompt confirmation-prompt}]
      [(WithConfirmation-> WithConfirmation-opts)])))
