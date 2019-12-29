(ns mftickets-web.components.template-form
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.form :as components.form]
            [mftickets-web.components.input :as components.input]
            [com.rpl.specter :as s]
            [mftickets-web.components.template-form.inputs :as inputs]
            [mftickets-web.components.factories.input :as factories.input]
            [mftickets-web.components.template-form.handlers :as handlers]))

;; Specs
(spec/def :template-form.template/id (spec/or :nil nil? :int int?))

(spec/def :template-form.template/name string?)

(spec/def :template-form.messages/on-edited-template-change fn?)

(spec/def :template-form/edited-template
  (spec/nilable
   (spec/keys :req-un [:template-form.template/id :template-form.template/name])))

(spec/def :template-form/original-template :template-form/edited-template)
(spec/def :template-form/properties-types (spec/nilable (spec/coll-of keyword?)))
(spec/def :template-form/submit-button-style ::components.form/submit-button-styles)
(spec/def :template-form/submit-button-label string?)

(spec/def :template-form/props
  (spec/keys :req [:template-form/original-template
                   :template-form/edited-template
                   :template-form/properties-types]
             :opt [:template-form.messages/on-edited-template-change]))

;; Helpers
(defn- render-input
  "Renders an input given the props and input metadata."
  [{:template-form/keys [edited-template] :as props}
   metadata]

  {:pre [(spec/assert :factories/input metadata)
         (spec/assert :template-form/props props)]}

  (let [handlers
        {:template-form.handlers/on-input-change
         #(handlers/on-input-change props metadata %)
         
         :template-form.handlers/on-add-template-section
         #(handlers/on-add-template-section props)}

        context
        (select-keys props [:template-form/properties-types])

        metadata*
        (-> metadata
            (assoc :factories.input/handlers handlers)
            (assoc :factories.input/parent-context context))]
  
    (factories.input/input-factory metadata* edited-template)))

;; Components
(defn template-form
  "A form representing a template."
  [{:template-form/keys [inputs-metadatas edited-template submit-button-style submit-button-label]
    :template-form.messages/keys [on-edited-template-submit]
    :or {inputs-metadatas [inputs/id inputs/name inputs/project-id inputs/creation-date
                           inputs/sections]
         on-edited-template-submit #(do nil)}
    :as props}]

  {:pre [(spec/assert :template-form/props props)]}

  (when edited-template
    [components.form/form {:on-submit #(on-edited-template-submit)
                           :button-style submit-button-style
                           :button-text submit-button-label}
     (for [input-metadata inputs-metadatas]
       (render-input props input-metadata))]))
