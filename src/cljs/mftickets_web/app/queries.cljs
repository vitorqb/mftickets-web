(ns mftickets-web.app.queries
  (:require [com.rpl.specter :as s]))

(defn get-app-metadata [x]
  "Reads the app metadata, which is the global app state."
  (when-let [response (:app-metadata-response x)]
    (when (:success response)
      (:body response))))

(defn projects
  "Reads the projects from the app metadata."
  [x]
  (some-> x get-app-metadata :projects))

(defn- active-project-id [x] (:active-project-id x))

(defn active-project
  "Reads the active project."
  [x]
  (let [projects* (projects x)
        active-project-id* (active-project-id x)]
    (if (nil? active-project-id*)
      (first projects*)
      (s/select-first [s/ALL #(= (:id %) active-project-id*)] projects*))))

(defn current-routing-match
  "The current routing match, as a reitit match."
  [x]
  (:current-routing-match x))
