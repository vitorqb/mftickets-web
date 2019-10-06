(ns mftickets-web.http
  (:require
   [cljs-http.client :as http]))

(def base-request
  {:with-credentials? true
   :accept "application/json"})

(defn wrap-auth
  [request token]
  (assoc-in request [:headers "authorization"] (str "Bearer " token)))

(defn send-key
  "Makes a post request for sending a key to the email."
  [_]
  (fn i-send-key [params]
    (http/post
     "/api/login/send-key"
     (assoc base-request :edn-params params))))

(defn get-token
  "Makes a post request for retrieving a token"
  [_]
  (fn i-get-token [params]
    (http/post
     "/api/login/get-token"
     (assoc base-request :edn-params params))))

(defn get-app-metadata
  "Makes a post request for retrieving the app metadata"
  [{:keys [token]}]
  (fn i-get-app-metadata []
    (http/get
     "/api/app-metadata"
     (-> base-request (wrap-auth token)))))

(defn get-token-from-cookies
  "Makes a get request for retrieving a token"
  [_]
  #(http/get "/api/login/get-token"))

(defn ping
  "Makes a ping post request"
  [{:keys [token]}]
  (fn i-ping []
    (http/get
     "/api/ping"
     (-> base-request (wrap-auth token)))))

(defn get-templates
  "Makes a get requests for the templates."
  [{:keys [token]}]
  (fn i-get-templates [{:keys [project-id]}]
    (http/get
     "/api/templates"
     (-> base-request
         (wrap-auth token)
         (assoc :query-params {:project-id project-id})))))

(defn get-projects
  "Makes a get request for the projects of an user."
  [{:keys [token]}]
  (fn i-get-projects []
    (http/get "/api/projects" (-> base-request (wrap-auth token)))))

(defn edit-project
  "Makes a PUT request for a project."
  [{:keys [token]}]
  (fn i-edit-project [{:keys [id] :as edited-project}]
    (let [params (select-keys edited-project [:name :description])]
      (http/put
       (str "/api/projects/" id)
       (-> base-request
           (wrap-auth token)
           (assoc :edn-params params))))))

(defn create-project
  "Makes a POST request for creating a project."
  [{:keys [token]}]
  (fn i-create-project [raw-project]
    (let [params (select-keys raw-project [:name :description])]
      (http/post
       "/api/projects"
       (-> base-request
           (wrap-auth token)
           (assoc :edn-params params))))))

(defn http-getter
  "Prepares a lookable object for http functions.
  `http-fns` is a map where each value is a curried function accepting options.
  Everytime the getter returns a value, it injects the current options from @app-state."
  [http-fns app-state]
  (reify
    ILookup
    (-lookup [this k]
      (-lookup this k nil))

    (-lookup [this k not-found]
      (if-let [f (k http-fns)]
        (let [opts (select-keys @app-state [:token])]
          (f opts))
        not-found))))
