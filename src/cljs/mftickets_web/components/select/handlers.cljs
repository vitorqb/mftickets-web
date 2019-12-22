(ns mftickets-web.components.select.handlers
  (:require [cljs.core.async :as async]
            [mftickets-web.domain.kw :as domain.kw]))

(defn on-change [{:select.messages/keys [on-select-change]} new-value]
  (-> new-value (js->clj :keywordize-keys true) on-select-change))

(defn after-load-options [matching-options callback]
  (-> matching-options (clj->js :keyword-fn domain.kw/kw->str) callback))

(defn on-load-options [props input-value callback]
  (let [get-matching-options (:select.async/get-matching-options props)
        _ (assert (fn? get-matching-options))]
    (async/go (-> input-value get-matching-options async/<! (after-load-options callback)))))
