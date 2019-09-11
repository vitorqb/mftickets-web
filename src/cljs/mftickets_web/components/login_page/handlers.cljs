(ns mftickets-web.components.login-page.handlers
  (:require
   [mftickets-web.components.login-page.reducers :as reducers]
   [mftickets-web.components.login-page.queries :as queries]
   [cljs.core.async :as async]))

(defn email-submit
  "Handler for submitting an email."
  [{:keys [state reduce! http]}]
  (let [send-key! (:send-key http)
        email (-> state queries/email-input-state :value)]
    (fn []
      (reduce! (reducers/before-email-submit))
      (async/go (-> {:email email} send-key! async/<! reducers/after-email-submit reduce!)))))

(defn key-submit
  "handler to submit the key."
  [{:keys [state reduce! http send-message!]}]
  (let [get-token! (:get-token http)
        key (-> state queries/key-input-state :value)
        email (-> state queries/email-input-state :value)
        params {:keyValue key :email email}]
    (fn []
      (reduce! (reducers/before-key-submit))
      (async/go
        (let [response (-> params get-token! async/<!)]
          (-> response reducers/after-key-submit reduce!)
          (->> response :body :token (send-message! :update-token)))))))
