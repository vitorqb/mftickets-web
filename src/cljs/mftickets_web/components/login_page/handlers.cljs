(ns mftickets-web.components.login-page.handlers
  (:require
   [mftickets-web.components.login-page.reducers :as reducers]
   [mftickets-web.components.login-page.queries :as queries]
   [cljs.core.async :as async]
   [mftickets-web.events.protocols :as events.protocols]))

(defn email-change
  "Handler for changing the email value."
  [new-value]
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-email-value new-value))))

(defn email-submit--after
  [response]
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/after-email-submit response))))

(defn email-submit
  "Handler for submitting an email."
  [{{:keys [send-key]} :http :keys [state]}]
  (let [email (-> @state queries/email-input-state :value)]

    (reify events.protocols/PEvent

      (reduce! [_] (reducers/before-email-submit))

      (run-effects! [_]
        (async/go [(->> {:email email} send-key async/<! email-submit--after)])))))

(defn key-change
  "Handler for changing the key value."
  [new-value]
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-key-value new-value))))

(defn key-submit--after
  [{{:keys [update-token->]} :events :as props} response]

  (reify events.protocols/PEvent

    (reduce! [_] (reducers/after-key-submit response))

    (propagate! [_] [(->> response :body :token (update-token-> props))])))

(defn key-submit
  "Handler to submit a key."
  [{{:keys [get-token]} :http :keys [state] :as props}]

  (let [key (-> @state queries/key-input-state :value)
        email (-> @state queries/email-input-state :value)
        params {:keyValue key :email email}]

    (reify events.protocols/PEvent

      (reduce! [_] (reducers/before-key-submit))

      (run-effects! [_]
        (async/go [(->> params get-token async/<! (key-submit--after props))])))))
