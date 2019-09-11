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
  [{:keys []}]
  nil)
