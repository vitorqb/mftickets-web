(ns mftickets-web.components.template-form
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.form :as components.form]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-sections-form :as components.template-sections-form]
            [com.rpl.specter :as s]
            [mftickets-web.components.template-form.inputs :as inputs]
            [mftickets-web.components.factories.input :as factories.input]))

;; Specs
(spec/def :template-form.template/id
  int?)

(spec/def :template-form.template/name
  string?)

(spec/def :template-form/edited-template
  (spec/nilable
   (spec/keys :req-un [:template-form.template/id :template-form.template/name])))

(spec/def :template-form/original-template :template-form/edited-template)

(spec/def :template-form/props
  (spec/keys :req [:template-form/original-template :template-form/edited-template]))

;; Helpers
(defn- render-input
  "Renders an input given the for props and input metadta."
  [{:template-form/keys [edited-template] :as props} metadata]

  {:pre [(spec/assert :factories/input metadata)
         (spec/assert :template-form/props props)]}
  
  (factories.input/input-factory props metadata edited-template))

;; Components
(defn template-form
  "A form representing a template."
  [{:template-form/keys [inputs-metadatas edited-template]
    :or {inputs-metadatas [inputs/id inputs/name inputs/project-id inputs/creation-date
                           inputs/sections]}
    :as props}]

  {:pre [(spec/assert :template-form/props props)]}

  (when edited-template
    [components.form/form {}
     (for [input-metadata inputs-metadatas]
       (render-input props input-metadata))]))
