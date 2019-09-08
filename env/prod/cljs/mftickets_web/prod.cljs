(ns mftickets-web.prod
  (:require [mftickets-web.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
