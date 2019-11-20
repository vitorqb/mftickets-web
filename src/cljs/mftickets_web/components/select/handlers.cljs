(ns mftickets-web.components.select.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.core.async :as async]))

(defn on-change [{:select.messages/keys [on-select-change]} new-value]
  (-> new-value (js->clj :keywordize-keys true) on-select-change))

(defrecord OnLoadOptions--after [matching-options callback]
  events.protocols/PEvent
  (run-effects! [_]
    (-> matching-options clj->js callback)
    nil))

(defrecord OnLoadOptions [props input-value callback]
  events.protocols/PEvent
  (run-effects! [_]
    (let [get-matching-options (:select.async/get-matching-options props)
          _ (assert (fn? get-matching-options))]
      (async/go
        [(-> input-value get-matching-options async/<! (->OnLoadOptions--after callback))]))))
