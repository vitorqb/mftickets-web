(ns mftickets-web.components.select.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]))

(defn on-change
  "A handler when the selected option changes."
  [{{:keys [on-change->]} :events} new-value]
  ^{::name "on-change"}
  (reify events.protocols/PEvent
    (propagate! [_]
      [(-> new-value (js->clj :keywordize-keys true) on-change->)])))
