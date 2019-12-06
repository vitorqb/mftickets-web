(ns mftickets-web.components.template-sections-form.handlers
  (:require [com.rpl.specter :as s]))

(defn- update-section
  "Updates a specific section from a seq of sections using update-fn"
  [sections section update-fn]
  (s/transform
   [(s/filterer #(= (:id %) (:id section))) s/FIRST]
   update-fn
   sections))

(defn on-template-section-input-change
  [{:template-sections-form.messages/keys [on-section-change]
    :template-sections-form.impl/keys [section]
    :template-sections-form/keys [sections]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(ifn? on-section-change) (ifn? update-value-fn)]}

  (-> sections
      (update-section section #(update-value-fn % new-value))
      (on-section-change)))
