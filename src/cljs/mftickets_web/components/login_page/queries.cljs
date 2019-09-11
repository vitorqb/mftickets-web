(ns mftickets-web.components.login-page.queries)

(def email-submission-possible-current-state #{:idle :ongoing})
(def key-submission-possible-current-state #{:idle :ongoing})

(defn email-input-state
  [state]
  (some-> state :inputs :email))

(defn key-input-state
  [state]
  (some-> state :inputs :key))

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

(defn key-submission-response
  [state]
  (some-> state :key-submission :response))

(defn key-submission-current-state
  [state]
  {:post [(key-submission-possible-current-state %)]}
  (or (some-> state :key-submission :current-state)
      :idle))

(defn user-message
  [state]
  {:post [(or (nil? %) (string? %))]}
  (cond

    (some-> state email-submission-response :success not)
    "Something went wrong on submitting the email."

    (some-> state key-submission-response :success not)
    "Something went wrong on submitting the key."

    (some-> state key-submission-response :success)
    "Token acquired!"))
