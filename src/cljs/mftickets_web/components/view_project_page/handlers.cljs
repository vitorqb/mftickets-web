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
(defn on-picked-project-change
  "Handler for when a project is picked by the user."
  [picked-project]

  ^{::name "on-picked-project-change"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-picked-project picked-project))))

(defn on-delete-picked-project--perform--after
  [{{:keys [refresh-app-metadata]} :events} response]
  {:pre [(fn? refresh-app-metadata)]}

  ^{::name "on-delete-picked-project--perform--after"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/after-delete-picked-project response))
    (propagate! [_] [(refresh-app-metadata)])))

(defn on-delete-picked-project--perform
  "Actually performs deletion after user confirmation."
  [{{:keys [delete-project]} :http :as props} project]
  {:pre [(fn? delete-project)]}

  ^{::name "on-delete-picked-project--perform"}
  (reify events.protocols/PEvent
    (run-effects! [_]
      (async/go
        [(->> project
              delete-project
              async/<!
              (on-delete-picked-project--perform--after props))]))))

(defn on-delete-picked-project
  "Handler for deleting the currently picked project."
  [{{:keys [with-confirmation->]} :events
    :keys [state]
    :as props}]
  {:pre [(fn? with-confirmation->) (satisfies? IDeref state)]}

  ^{::name "on-delete-picked-project"}
  (reify events.protocols/PEvent

    (propagate! [_]

      (let [picked-project (queries/picked-project @state)
            perform-event (on-delete-picked-project--perform props picked-project)
            confirmation-prompt (on-delete-picked-project-prompt picked-project)
            with-confirmation-opts {:props props
                                    :event perform-event
                                    :prompt confirmation-prompt}]

        [(with-confirmation-> with-confirmation-opts)]))))
