(ns mftickets-web.components.template-sections-form.handlers
  (:require [com.rpl.specter :as s]
            [mftickets-web.domain.template-section :as domain.template-section]
            [mftickets-web.domain.template-property :as domain.template-property]))

(defn- update-section
  "Updates a specific section from a seq of sections using update-fn"
  [sections section update-fn]
  (s/transform
   [(s/filterer #(domain.template-section/same-id? section %)) s/FIRST]
   update-fn
   sections))

(defn on-template-section-input-change
  [{:template-sections-form.messages/keys [on-sections-change]
    :template-sections-form.impl/keys [section]
    :template-sections-form/keys [sections]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(ifn? on-sections-change) (ifn? update-value-fn)]}

  (-> sections
      (update-section section #(update-value-fn % new-value))
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
    (->> sections
         (s/transform [(s/filterer #(domain.template-section/same-id? % section)) s/FIRST :properties]
                      #(concat [new-property] %))
         on-sections-change)))
