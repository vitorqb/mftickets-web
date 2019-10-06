(ns mftickets-web.components.create-project-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.create-project-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.create-project-page.queries :as queries]))

(defn on-raw-project-change
  "Reacts to a raw project change."
  [new-raw-project]

  ^{::name "on-raw-project-change"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-raw-project new-raw-project))))

(defn on-create-project-submit--before
  []
  ^{::name "on-create-project-submit--before"}
  (reify events.protocols/PEvent
    (reduce! [_] (comp (reducers/set-create-project-response nil)
                       (reducers/set-loading? true)))))

(defn on-create-project-submit--after
  [{{:keys [refresh-app-metadata->]} :events
    :as props}
   created-project-response]
  {:pre [(fn? refresh-app-metadata->)]}

  ^{::name "on-create-project-submit--after"}
  (reify events.protocols/PEvent
    (reduce! [_] (comp (reducers/set-create-project-response created-project-response)
                       (reducers/set-loading? false)))
    (propagate! [_] [(refresh-app-metadata->)])))

(defn on-create-project-submit
  "Reacts to the user submitting a project to be created."
  [{{:keys [create-project]} :http
    :keys [state]
    :as props}]
  {:pre [(fn? create-project)
         (satisfies? ISwap state)]}

  ^{::name "on-create-project-submit"}
  (reify events.protocols/PEvent

    (dispatch! [_] [(on-create-project-submit--before)])

    (run-effects! [_]
      (async/go
        [(->> @state
              queries/raw-project
              create-project
              async/<!
              (on-create-project-submit--after props))]))))
