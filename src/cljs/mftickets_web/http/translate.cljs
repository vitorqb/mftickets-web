(ns mftickets-web.http.translate
  "Namespace dedicated to translating objects to and from http requests."
  (:require [com.rpl.specter :as s]
            [mftickets-web.domain.template :as domain.template]))

(defn template->create-template
  "Transforms a user-inputed template into a format expected for creation of a template."
  [template]
  (select-keys template [:id :name :creation-date :project-id :sections]))

(defn parse-be-template
  "Parses a BE template, ensuring it's format is as expected by the FE."
  [template]
  (domain.template/set-value-types-to-keyword template))

(defn parse-paged-be-templates
  "Parses a paged be response with templates."
  [response]
  (if (and (:success response) (-> response :body :items))
    (s/transform [:body :items s/ALL] parse-be-template response)
    response))

(def template->edit-template template->create-template)
