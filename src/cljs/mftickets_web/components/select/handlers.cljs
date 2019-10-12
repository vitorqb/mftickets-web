(ns mftickets-web.components.select.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]))

(defrecord Change [props new-value]
  events.protocols/PEvent
  (propagate! [_]
    (let [Change-> (-> props :events :Change->)]
      [(-> new-value (js->clj :keywordize-keys true) Change->)])))
