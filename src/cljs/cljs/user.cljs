(ns cljs.user
  (:require
   [mftickets-web.core]))

(defn get-app-state [] mftickets-web.core/app-state)
(defn set-token [x] (swap! mftickets-web.core/app-state assoc :token x))
