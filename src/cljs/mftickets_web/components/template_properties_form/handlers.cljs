(ns mftickets-web.components.template-properties-form.handlers
  (:require [com.rpl.specter :as s]
            [mftickets-web.domain.template-property :as domain.template-property]))

(defn on-template-property-change
  [{:template-properties-form.impl/keys [property]
    :template-properties-form/keys [properties]
    :template-properties-form.messages/keys [on-properties-change]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(ifn? update-value-fn) (ifn? on-properties-change)]}

  (->> properties
       (s/transform [(s/filterer #(domain.template-property/same-id? % property)) s/FIRST]
                    #(update-value-fn % new-value))
       on-properties-change))

(defn on-remove-template-property
  [{:template-properties-form.impl/keys [property]
    :template-properties-form/keys [properties]
    :template-properties-form.messages/keys [on-properties-change]}]
  (->> properties
       (s/setval [(s/filterer #(domain.template-property/same-id? % property)) s/ALL] s/NONE)
       on-properties-change))
