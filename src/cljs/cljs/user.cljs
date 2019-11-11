(ns cljs.user
  (:require
   [mftickets-web.core]
   [mftickets-web.app.handlers :as app.handlers]
   [mftickets-web.events :as events]
   [com.rpl.specter :as s]
   [cljs.spec.alpha :as spec]))

(defn get-app-state [] mftickets-web.core/app-state)

(defn set-token [x]
  (let [props {:state mftickets-web.core/app-state
               :http mftickets-web.core/http}]
    (events/react! props (app.handlers/->UpdateToken props x))))

(defn set-events-log
  ([] (set-events-log true))
  ([x] (reset! events/print? x)))

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
