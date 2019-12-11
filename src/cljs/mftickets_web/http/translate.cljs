(ns mftickets-web.http.translate
  "Namespace dedicated to translating objects to and from http requests."
  (:require [com.rpl.specter :as s]))

(defn template->create-template
  "Transforms a user-inputed template into a format expected for creation of a template."
  [template]
  (-> template
      (select-keys [:id :name :creation-date :project-id :sections])
      (->> (s/transform [:sections s/ALL :properties s/ALL :value-type] keyword))))

(def template->edit-template template->create-template)
