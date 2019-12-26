(ns mftickets-web.components.template-properties-form.handlers
  (:require [com.rpl.specter :as s]
            [mftickets-web.domain.template-property :as domain.template-property]
            [mftickets-web.domain.sequences :as domain.sequences]))

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
       domain.sequences/update-order
       on-properties-change))

(defn on-remove-template-property
  [{:template-properties-form.impl/keys [property]
    :template-properties-form/keys [properties]
    :template-properties-form.messages/keys [on-properties-change]}]
  (->> properties
       (s/setval [(s/filterer #(domain.template-property/same-id? % property)) s/ALL] s/NONE)
       on-properties-change))

(defn on-move-template-property-back
  [{:template-properties-form.impl/keys [property]
    :template-properties-form/keys [properties]
    :template-properties-form.messages/keys [on-properties-change]}]
  (on-properties-change
   (->> properties
        (domain.sequences/move-back #(= (:id %) (:id property)))
        domain.sequences/update-order
        (into []))))

(defn on-move-template-property-forward
  [{:template-properties-form.impl/keys [property]
    :template-properties-form/keys [properties]
    :template-properties-form.messages/keys [on-properties-change]}]
  (on-properties-change
   (->> properties
        (domain.sequences/move-forward #(= (:id %) (:id property)))
        domain.sequences/update-order
        (into []))))
