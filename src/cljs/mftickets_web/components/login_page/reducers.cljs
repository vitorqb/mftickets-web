(ns mftickets-web.components.login-page.reducers)

(defn set-email-value [x]
  #(assoc-in % [:inputs :email :value] x))

(defn set-key-value [x]
  #(assoc-in % [:inputs :key :value] x))

(defn before-email-submit
  "Reduces the state before an email submission."
  []
  #(-> %
       (assoc-in [:email-submission :response] {})
       (assoc-in [:email-submission :current-state] :ongoing)))

(defn after-email-submit
  "Reduces the state after an email submission."
  [response]
  #(-> %
       (assoc-in [:email-submission :current-state] :idle)
       (assoc-in [:email-submission :response] response)))
