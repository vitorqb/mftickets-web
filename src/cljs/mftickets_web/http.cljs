(ns mftickets-web.http
  (:require
   [cljs-http.client :as http]))

(defn send-key
  "Makes a post request for sending a key to the email."
  [params]
  (http/post
   "http://127.0.0.1:3000/api/login/send-key"
   {:with-credentials? false
    :json-params params}))

