(ns mftickets-web.components.template-picker.handlers
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.domain.template :as domain.template]))

(defn on-select-change
  [{:template-picker.messages/keys [on-template-picked]} picked-option]

  {:pre [(spec/assert fn? on-template-picked)]}

  ;; We need to convert value-types back to keyword because when parsing it to the js
  ;; world it becomes a string.
  (-> picked-option :value domain.template/set-value-types-to-keyword on-template-picked))
