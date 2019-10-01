(ns mftickets-web.components.projects-page.queries)

(defn loading? [x] (:loading? x false))
(defn fetch-projects-response [x] (:fetch-projects-response x))
(defn projects [x]
  (if-let [response (fetch-projects-response x)]
    (when (:success response)
      (:body response))))
