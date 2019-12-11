(ns mftickets-web.http
  (:require
   [cljs-http.client :as http]
   [cljs.core.async :as async]
   [com.rpl.specter :as s]
   [mftickets-web.http.translate :as translate]))

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
  "Makes a get requests for the templates.
  The returning fn accepts:
  - project-id: the id of the project for which templates are queried.
  - name-like:  a string that should match the template name."
  [{:keys [token]}]

  (fn i-get-templates [{:keys [project-id name-like] :pagination/keys [page-number page-size]}]

    (let [params (cond-> {:project-id project-id}
                   name-like (assoc :name-like name-like)
                   page-number (assoc :pageNumber page-number)
                   page-size (assoc :pageSize page-size))
          request (-> base-request (wrap-auth token) (assoc :query-params params))]

      (http/get "/api/templates" request))))

(defn edit-template
  "Makes a POST request for editing a template"
  [{:keys [token]}]
  (fn i-edit-template [{:keys [id] :as edited-template}]
    (let [params (translate/template->edit-template edited-template)]
      (http/post
       (str "/api/templates/" id)
       (-> base-request
           (wrap-auth token)
           (assoc :edn-params params))))))

(defn create-template
  "Makes a POST request for creating a template"
  [{:keys [token]}]
  (fn i-create-template [template]
    (let [params (translate/template->create-template template)]
      (http/post
       "/api/templates"
       (-> base-request
           (wrap-auth token)
           (assoc :edn-params params)
           (assoc :query-params {:project-id (:project-id params)}))))))

(defn get-matching-templates
  "Wrapper aroung get-templates that returns the list of templates on the first page of
  the `get-templates` response."
  [{:keys [token]}]

  (fn i-get-matching-templates [opts]
    (async/go
      (let [get-templates* (get-templates {:token token})
            response (-> opts get-templates* async/<!)]
        (-> response :body :items)))))

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

(defn delete-project
  "Makes a DELETE request for deleting a project."
  [{:keys [token]}]
  (fn i-delete-project [{:keys [id]}]
    {:pre [(int? id)]}
    (http/delete
     (str "/api/projects/" id)
     (-> base-request (wrap-auth token)))))

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
