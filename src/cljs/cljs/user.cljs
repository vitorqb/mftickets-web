(ns cljs.user
  (:require
   [mftickets-web.core]))

(defn get-app-state [] mftickets-web.core/app-state)
