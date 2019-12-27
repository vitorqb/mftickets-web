(ns mftickets-web.domain.template
  (:require [mftickets-web.domain.kw :as domain.kw]
            [com.rpl.specter :as s]
            [mftickets-web.domain.sequences :as domain.sequences]))

(defn gen-empty-template [{:keys [project-id]}]
  {:id nil
   :name ""
   :project-id project-id
   :creation-date nil
   :sections []})

(defn set-value-types-to-keyword [template]
  "Given a template, set's it's properties' type values to be a keyword."
  (s/transform [:sections s/ALL :properties s/ALL :value-type] domain.kw/str->kw template))

(defn prepend-section
  "Prepends a section to a template :sections. Handlers updating `:order`."
  [template section]
  (->> template
       (s/setval [:sections s/BEFORE-ELEM] section)
       (s/transform :sections domain.sequences/update-order)))
