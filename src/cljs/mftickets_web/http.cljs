(ns mftickets-web.http
  (:require
   [cljs-http.client :as http]))

(def base-request
  {:with-credentials? false
   :accept "application/edn"})

(defn send-key
  "Makes a post request for sending a key to the email."
  [{:keys [token]}]
  (fn i-send-key [params]
    (http/post
     "http://127.0.0.1:3000/api/login/send-key"
     (assoc base-request :edn-params params))))

(defn get-token
  "Makes a post request for retrieving a token"
  [{:keys [token]}]
  (fn i-get-token [params]
    (http/post
     "http://127.0.0.1:3000/api/login/get-token"
     (assoc base-request :edn-params params))))

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