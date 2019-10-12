(ns mftickets-web.components.templates-page.handlers
  (:require
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.components.templates-page.reducers :as reducers]
   [cljs.core.async :as async]))

(defrecord FetchTemplates--after [response]
  events.protocols/PEvent
  (reduce! [_] (reducers/set-templates-http-response response)))

(defrecord FetchTemplates [props]
  events.protocols/PEvent
  (run-effects! [_]
    (let [{{:keys [get-templates]} :http :templates-page/keys [current-project-id]} props]
      (async/go [(-> {:project-id current-project-id}
                     get-templates
                     async/<!
                     ->FetchTemplates--after)]))))

(defrecord Init [props]
  events.protocols/PEvent
  (dispatch! [_] [(->FetchTemplates props)]))
