(ns mftickets-web.components.template-form.handlers
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.domain.template-section :as domain.template-section]
            [mftickets-web.domain.template :as domain.template]))

;; Handlers
(defn on-input-change
  "Represents an input change. `metadata` is the input metadata. `new-value`
  is the new value for the input."
  [{:template-form.messages/keys [on-edited-template-change] :template-form/keys [edited-template]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(fn? on-edited-template-change)]}
  
  (-> edited-template (update-value-fn new-value) on-edited-template-change))

(defn on-add-template-section
  [{:template-form.messages/keys [on-edited-template-change]
    :template-form/keys [edited-template]}]
  (let [empty-section-args {:template-id (:id edited-template)}
        empty-section (domain.template-section/gen-empty-template-section empty-section-args)]
    (-> edited-template
        (domain.template/prepend-section empty-section)
        on-edited-template-change)))
