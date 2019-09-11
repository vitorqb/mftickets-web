(ns mftickets-web.components.login-page.queries)

(def email-submission-possible-current-state #{:idle :ongoing})

(defn email-input-state
  [state]
  (some-> state :inputs :email))

(defn email-submission-response
  [state]
  (some-> state :email-submission :response))

(defn email-has-been-submited-sucessfully?
  [state]
  {:post [(or (nil? %) (boolean? %))]}
  (some-> state email-submission-response :status (= 204)))

(defn email-submission-current-state
  [state]
  {:post [(email-submission-possible-current-state %)]}
  (or (some-> state :email-submission :current-state)
      :idle))
