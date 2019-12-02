(ns mftickets-web.components.template-form.handlers
  (:require [cljs.spec.alpha :as spec]))

(defn on-input-change
  "Represents an input change. `metadata` is the input metadata. `new-value`
  is the new value for the input."
  [{:template-form.messages/keys [on-edited-template-change] :template-form/keys [edited-template]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(fn? on-edited-template-change)]}
  
  (-> edited-template (update-value-fn new-value) on-edited-template-change))
