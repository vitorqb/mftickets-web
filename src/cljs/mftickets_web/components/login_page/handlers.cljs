(ns mftickets-web.components.login-page.handlers
  (:require
   [mftickets-web.components.login-page.reducers :as reducers]
   [mftickets-web.components.login-page.queries :as queries]
   [cljs.core.async :as async]))

(defn after-email-submit [{:keys [state]} response]
  (swap! state (reducers/after-email-submit response)))

(defn on-email-submit [{state :state {:keys [send-key]} :http :as props}]
  (swap! state (reducers/before-email-submit))
  (let [email (-> @state queries/email-input-state :value)]
    (async/go (->> {:email email} send-key async/<! (after-email-submit props)))))

(defn after-key-submit [{:keys [state] :login-page.messages/keys [update-token]} response]
  (swap! state (reducers/after-key-submit response))
  (->> response :body :token update-token))

(defn on-key-submit [{state :state {:keys [get-token]} :http :as props}]
  (swap! state (reducers/before-key-submit))
  (let [state (:state props)
        key (-> @state queries/key-input-state :value)
        email (-> @state queries/email-input-state :value)
        params {:keyValue key :email email}]
    (async/go (->> params get-token async/<! (after-key-submit props)))))

(defn on-email-input-change [{:keys [state]} new-value]
  (swap! state (reducers/set-email-value new-value)))

(defn on-key-input-change [{:keys [state]} new-value]
  (swap! state (reducers/set-key-value new-value)))
