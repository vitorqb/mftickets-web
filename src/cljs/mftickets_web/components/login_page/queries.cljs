(ns mftickets-web.components.login-page.queries)

(def email-submission-possible-current-state #{:idle :ongoing})

(defn email-has-been-submited-sucessfully?
  [value]
  (some-> value :email-submission :response :status (= 204)))

(defn email-submission-current-state
  [value]
  {:post [(email-submission-possible-current-state %)]}
  (or (some-> value :email-submission :current-state)
      :idle))
