(ns mftickets-web.components.templates-page.queries
  (:require
   [com.rpl.specter :as s]))

(def ^:private default-page-size 50)

(defn is-loading? [x] (:is-loading? x))

(defn templates-http-response
  "The raw http response."
  [state]
  (:templates-http-response state))

(defn templates
  "A list of templates."
  [state]
  (if-let [response (templates-http-response state)]
    (when (:success response)
      (some-> response :body :items))))

(defn current-page
  "The current table page being displayed to the user."
  [x]
  (:current-page x 1))

(defn current-page-size
  "The current page size."
  [x]
  (:current-page-size x default-page-size))

(defn page-count
  "The total number of pages for the table."
  [x]
  (let [page-size (current-page-size x)]
    (some-> x templates-http-response :body :total-items-count (/ page-size) (js/Math.ceil))))
