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
