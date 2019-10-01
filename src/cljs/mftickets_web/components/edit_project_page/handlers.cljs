(ns mftickets-web.components.edit-project-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.edit-project-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.edit-project-page.queries :as queries]))

(defn on-picked-project-change
  "Stores a new picked project."
  [new-picked-project]

  ^{::name "on-picked-project-change"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/new-picked-project new-picked-project))))

(defn on-edited-project-change
  "Stores the edited project on change."
  [new-edited-project]

  ^{::name "on-edited-project-change"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-edited-project new-edited-project))))

(defn on-edited-project-submit--before []

  ^{::name "on-edited-project-submit--before"}
  (reify events.protocols/PEvent
    (reduce! [_] (comp (reducers/set-loading? true)
                       (reducers/set-edit-project-response nil)))))

(defn on-edited-project-submit--after
  [{{:keys [refresh-app-metadata->]} :events}
   edit-project-response]
  {:pre [(fn? refresh-app-metadata->)]}

  ^{::name "on-edited-project-submit--after"}
  (reify events.protocols/PEvent
    (reduce! [_] (comp (reducers/set-loading? false)
                       (reducers/edit-project-response edit-project-response)))
    (propagate! [_] [(refresh-app-metadata->)])))

(defn on-edited-project-submit
  "Handles a submission for edition of a project."
  [{{:keys [edit-project]} :http :keys [state] :as props}]
  {:pre [(fn? edit-project)]}

  ^{::name "on-edited-project-submit"}
  (reify events.protocols/PEvent

    (dispatch! [_] [(on-edited-project-submit--before)])

    (run-effects! [_]
      (async/go
        [(->> @state
              queries/edited-project
              edit-project
              async/<!
              (on-edited-project-submit--after props))]))))
