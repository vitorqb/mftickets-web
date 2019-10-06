(ns mftickets-web.app.queries)

(defn get-app-metadata [x]
  "Reads the app metadata, which is the global app state."
  (when-let [response (:app-metadata-response x)]
    (when (:success response)
      (:body response))))

(defn projects
  "Reads the projects from the app metadata."
  [x]
  (some-> x get-app-metadata :projects))
