(ns mftickets-web.components.template-properties-form.handlers
  (:require [com.rpl.specter :as s]))

(defn on-template-property-change
  [{:template-properties-form.impl/keys [property]
    :template-properties-form/keys [properties]
    :template-properties-form.messages/keys [on-properties-change]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(ifn? update-value-fn) (ifn? on-properties-change)]}

  (let [properties* (s/transform
                     [(s/filterer #(= (:id %) (:id property))) s/FIRST]
                     #(update-value-fn % new-value)
                     properties)]
    (on-properties-change properties*)))

(defn on-remove-template-property
  [{:template-properties-form.impl/keys [property]
    :template-properties-form/keys [properties]
    :template-properties-form.messages/keys [on-properties-change]}]
  (->> properties
       (s/setval [(s/filterer [:id #(= % (:id property))]) s/ALL] s/NONE)
       on-properties-change))
