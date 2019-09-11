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

(defn set-key-submit-response [x]
  #(assoc-in % [:key-submission :response] x))

(defn set-key-submission-state [x]
  #(assoc-in % [:key-submission :current-state] x))

(defn before-key-submit
  "Reduces the state before key is submited."
  []
  #(-> %
       ((set-key-submit-response nil))
       ((set-key-submission-state :ongoing))))

(defn after-key-submit
  "Reduces the state after key is submited."
  [response]
  #(-> %
       ((set-key-submit-response response))
       ((set-key-submission-state :idle))))
