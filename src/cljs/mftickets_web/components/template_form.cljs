(ns mftickets-web.components.template-form
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.form :as components.form]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-sections-form :as components.template-sections-form]
            [com.rpl.specter :as s]))

;; Specs
(spec/def :input-metadata/id
  keyword?)

(spec/def :input-metadata/label
  string?)

(spec/def :input-metadata/disabled
  (spec/nilable boolean?))

(spec/def :input-metadata/path
  (spec/or :keyword keyword? :seq-of-keyword (spec/coll-of keyword?)))

(spec/def :template-form/input-metadata
  (spec/keys :req [:input-metadata/id
                   :input-metadata/label
                   :input-metadata/path]
             :opt [:input-metadata/disabled]))

(spec/def :template-form.template/id
  int?)

(spec/def :template-form.template/name
  string?)

(spec/def :template-form/edited-template
  (spec/nilable
   (spec/keys :req-un [:template-form.template/id :template-form.template/name])))

(spec/def :template-form/original-template :template-form/edited-template)

(spec/def :template-form/inputs-metadatas
  (spec/nilable
   (spec/coll-of :template-form/input-metadata)))

(spec/def :template-form/props
  (spec/keys :req [:template-form/original-template :template-form/edited-template]))

;; Helpers
;; !!!! TODO -> This is duplicated from project form.
;; !!!!         Would be cool to try to abstract it later.
(def id-input-metadata
  #:input-metadata{:id :id :label "Id" :path :id})

(def name-input-metadata
  #:input-metadata{:id :name :label "Name" :path :name})

(def project-id-input-metadata
  #:input-metadata{:id :project-id :label "Project Id" :path :project-id})

(def creation-date-input-metadata
  #:input-metadata{:id :creation-data :label "Created At" :path :creation-date})

(def sections-form-metadata
  #:input-metadata{:id :sections
                   :label "Sections"
                   :path :sections
                   :component components.template-sections-form/template-sections-form})

(defn- input
  "Wrapper around `input` that uses `input-metadata`."
  [{:template-form/keys [edited-template]
    :input-metadata/keys [id label path disabled component]
    :or {component components.input/input}
    :as props}]

  {:pre [(spec/assert :template-form/input-metadata props)]}

  (let [value (or (s/select-first path edited-template) "")
        input-props {:input/label label
                     :input/disabled disabled
                     :input/value value
                     :parent-props props}]
    [component input-props]))

;; Components
(defn template-form
  "A form representing a template."
  [{:template-form/keys [inputs-metadatas]
    :or {inputs-metadatas [id-input-metadata name-input-metadata project-id-input-metadata
                           creation-date-input-metadata sections-form-metadata]}
    :as props}]

  {:pre [(spec/assert :template-form/props props)
         (spec/assert :template-form/inputs-metadatas inputs-metadatas)]}
  
  [components.form/form {}
   (for [{:input-metadata/keys [id] :as input-metadata} inputs-metadatas
         :let [props* (merge props input-metadata)]]
     ^{:key id} [input props*])])
