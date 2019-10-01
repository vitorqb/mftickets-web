(ns mftickets-web.components.project-picker.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as s]))

(defn on-change
  "An event to handle changing the currently picked project."
  [{{:keys [on-change->]} :events} new-option]
  {:pre [(s/valid? :select/option new-option)
         (s/valid? fn? on-change->)]}

  ^{::name "on-change"}
  (reify events.protocols/PEvent
    (events.protocols/propagate! [_] [(-> new-option :value on-change->)])))
