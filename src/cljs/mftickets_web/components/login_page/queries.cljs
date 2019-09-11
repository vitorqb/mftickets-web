(ns mftickets-web.components.login-page.queries)

(defn email-has-been-submited-sucessfully?
  [value]
  (some-> value :email-submission-response :status (= 204)))
