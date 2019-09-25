(ns mftickets-web.components.templates-page.queries
  (:require
   [com.rpl.specter :as s]))

(defn templates-http-response
  "The raw http response."
  [state]
  (:templates-http-response state))

(defn templates
  "A list of templates."
  [state]
  (if-let [response (templates-http-response state)]
    (when (:success response)
      (some-> response :body))))
