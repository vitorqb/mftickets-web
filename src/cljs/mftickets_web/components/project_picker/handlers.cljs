(ns mftickets-web.components.project-picker.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as s]))

(defrecord Change [props new-option]
  events.protocols/PEvent
  (events.protocols/propagate! [_]
    (let [Change-> (-> props :events :Change->)
          _ (assert (s/valid? :select/option new-option))
          _ (assert (s/valid? fn? Change->))]
      [(-> new-option :value Change->)])))
