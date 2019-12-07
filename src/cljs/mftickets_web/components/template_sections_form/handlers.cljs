(ns mftickets-web.components.template-sections-form.handlers
  (:require [com.rpl.specter :as s]
            [mftickets-web.domain.template-section :as domain.template-section]))

(defn- update-section
  "Updates a specific section from a seq of sections using update-fn"
  [sections section update-fn]
  (s/transform
   [(s/filterer #(= (:id %) (:id section))) s/FIRST]
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
       (remove #(= (domain.template-section/get-id %)
                   (domain.template-section/get-id section)))
       on-sections-change))
