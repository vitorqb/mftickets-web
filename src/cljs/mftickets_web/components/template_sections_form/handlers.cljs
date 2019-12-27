(ns mftickets-web.components.template-sections-form.handlers
  (:require [com.rpl.specter :as s]
            [mftickets-web.domain.template-section :as domain.template-section]
            [mftickets-web.domain.template-property :as domain.template-property]))

(defn on-template-section-input-change
  [{:template-sections-form.messages/keys [on-sections-change]
    :template-sections-form.impl/keys [section]
    :template-sections-form/keys [sections]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(ifn? on-sections-change) (ifn? update-value-fn)]}

  (-> sections
      (domain.template-section/update-section-in-coll section #(update-value-fn % new-value))
      (on-sections-change)))

(defn on-template-section-remove
  [{:template-sections-form.messages/keys [on-sections-change]
    :template-sections-form.impl/keys [section]
    :template-sections-form/keys [sections]}]
  (->> sections
       (remove #(domain.template-section/same-id? section %))
       on-sections-change))

(defn on-add-template-property
  [{:template-sections-form.messages/keys [on-sections-change]
    :template-sections-form/keys [sections]
    :template-sections-form.impl/keys [section]}]
  (let [new-property-args {:template-section-id (:id section)}
        new-property (domain.template-property/gen-empty-template-property new-property-args)]
    (-> sections
        (domain.template-section/prepend-property-to-coll section new-property)
        (domain.template-section/update-properties-order-from-coll section)
        on-sections-change)))
