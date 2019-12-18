(ns mftickets-web.components.template-form
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.form :as components.form]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-sections-form :as components.template-sections-form]
            [com.rpl.specter :as s]
            [mftickets-web.components.template-form.inputs :as inputs]
            [mftickets-web.components.factories.input :as factories.input]
            [mftickets-web.components.template-form.handlers :as handlers]))

;; Specs
(spec/def :template-form.template/id
  (spec/or :nil nil? :int int?))

(spec/def :template-form.template/name
  string?)

(spec/def :template-form.messages/on-edited-template-change
  fn?)

(spec/def :template-form/edited-template
  (spec/nilable
   (spec/keys :req-un [:template-form.template/id :template-form.template/name])))

(spec/def :template-form/original-template :template-form/edited-template)

(spec/def :template-form/props
  (spec/keys :req [:template-form/original-template
                   :template-form/edited-template]
             :opt [:template-form.messages/on-edited-template-change]))

;; Helpers
(defn- render-input
  "Renders an input given the for props and input metadta."
  [{:template-form/keys [edited-template] :as props}
   metadata]

  {:pre [(spec/assert :factories/input metadata)
         (spec/assert :template-form/props props)]}

  (let [input-change #(handlers/on-input-change props metadata %)
        ;; !!!! TODO - Find a better way
        metadata* (assoc metadata
                         :input.messages/on-change
                         input-change
                         :template-sections-form.messages/on-sections-change
                         input-change
                         :template-form.sections-actions-buttons.messages/on-add-template-section
                         #(handlers/on-add-template-section props))]
  
    (factories.input/input-factory metadata* edited-template)))

;; Components
(defn template-form
  "A form representing a template."
  [{:template-form/keys [inputs-metadatas edited-template]
    :template-form.messages/keys [on-edited-template-submit]
    :or {inputs-metadatas [inputs/id inputs/name inputs/project-id inputs/creation-date
                           inputs/sections]
         on-edited-template-submit #(do nil)}
    :as props}]

  {:pre [(spec/assert :template-form/props props)]}

  (when edited-template
    [components.form/form {:on-submit #(on-edited-template-submit)}
     (for [input-metadata inputs-metadatas]
       (render-input props input-metadata))]))
