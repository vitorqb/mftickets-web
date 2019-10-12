(ns mftickets-web.components.login-page.handlers
  (:require
   [mftickets-web.components.login-page.reducers :as reducers]
   [mftickets-web.components.login-page.queries :as queries]
   [cljs.core.async :as async]
   [mftickets-web.events.protocols :as events.protocols]))

(defrecord EmailChange [new-value]
  events.protocols/PEvent
  (reduce! [_] (reducers/set-email-value new-value)))

(defrecord EmailSubmit--after [response]
  events.protocols/PEvent
  (reduce! [_] (reducers/after-email-submit response)))

(defrecord EmailSubmit [props]
  events.protocols/PEvent
  (reduce! [_] (reducers/before-email-submit))
  (run-effects! [_]
    (let [{{:keys [send-key]} :http :keys [state]} props
          email (-> @state queries/email-input-state :value)]
      (async/go [(->> {:email email} send-key async/<! ->EmailSubmit--after)]))))

(defrecord KeyChange [new-value]
  events.protocols/PEvent
  (reduce! [_] (reducers/set-key-value new-value)))

(defrecord KeySubmit--after [props response]
  events.protocols/PEvent
  (reduce! [_] (reducers/after-key-submit response))
  (propagate! [_]
    (let [UpdateToken-> (-> props :events :UpdateToken->)]
      [(->> response :body :token (UpdateToken-> props))])))

(defrecord KeySubmit [props]
  events.protocols/PEvent
  (reduce! [_] (reducers/before-key-submit))
  (run-effects! [_]
    (let [state (:state props)
          get-token (-> props :http :get-token)
          key (-> @state queries/key-input-state :value)
          email (-> @state queries/email-input-state :value)
          params {:keyValue key :email email}]
      (async/go [(->> params get-token async/<! (->KeySubmit--after props))]))))
