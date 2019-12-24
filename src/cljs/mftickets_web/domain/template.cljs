(ns mftickets-web.domain.template
  (:require [mftickets-web.domain.kw :as domain.kw]
            [com.rpl.specter :as s]))

(defn gen-empty-template [{:keys [project-id]}]
  {:id nil
   :name ""
   :project-id project-id
   :creation-date nil
   :sections []})

(defn set-value-types-to-keyword [template]
  "Given a template, set's it's properties' type values to be a keyword."
  (s/transform [:sections s/ALL :properties s/ALL :value-type] domain.kw/str->kw template))
