(ns cljs.user
  (:require
   [mftickets-web.core]
   [mftickets-web.app.handlers :as app.handlers]
   [com.rpl.specter :as s]
   [cljs.spec.alpha :as spec]
   [cljs.test :as t]
   [cljs.test-ns-requires]))

(defn get-app-state [] mftickets-web.core/app-state)

(defn set-token
  ([] (set-token "foo"))
  ([x] (mftickets-web.app.handlers/update-token mftickets-web.core/injections x)))

(spec/check-asserts true)

(defprotocol IDownloadable
  (download [o] "Downloads the object!"))

(extend-protocol IDownloadable
  string
  (download [s]
    (let [encoded (js/encodeURIComponent s)
          element (doto (js/document.createElement "a")
                    (.setAttribute "href" (str "data:text/plan;charset=utf-8," encoded))
                    (.setAttribute "download" "CLJS-DOWNLOAD"))]
      (js/document.body.appendChild element)
      (.click element)
      (js/document.body.removeChild element)
      nil))

  object
  (download [o]
    (download (pr-str o))))

(def pp cljs.pprint/pprint)
