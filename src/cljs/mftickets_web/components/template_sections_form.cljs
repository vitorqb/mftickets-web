(ns mftickets-web.components.template-sections-form
  (:require [mftickets-web.components.input :as components.input]
            [cljs.spec.alpha :as spec]
            [mftickets-web.components.template-sections-form.input :as input]
            [mftickets-web.components.factories.input :as factories.input]
            [mftickets-web.components.template-sections-form.handlers :as handlers]
            [mftickets-web.domain.template-section :as template-section]))

;; Scss
(def base-class "template-sections-form")
(def label-class (str base-class "__label"))
(def inputs-container-class (str base-class "__inputs-container"))
(def section-input-class (str base-class "__section-input"))

;; Specs
(spec/def :template-sections-form/sections (spec/coll-of any?))
(spec/def :template-sections-form/inputs-metadatas any?)

(spec/def :template-sections-form/props
  (spec/keys
   :req [:template-sections-form/sections]))

;; Helpers
(defn- render-input
  "Renders an input from it's metadata."
  [{:template-sections-form.impl/keys [section]
    :template-sections-form/keys [disabled]
    :as props}
   metadata]

  {:pre [(spec/assert :factories/input metadata)]}

  (let [on-change #(handlers/on-template-section-input-change props metadata %)
        metadata* (cond-> metadata
                    ;; !!!! TODO -> Find a way to generalize
                    :always
                    (assoc :input.messages/on-change
                           on-change
                           :template-properties-form.messages/on-properties-change
                           on-change
                           :template-sections-form.action-buttons.messages/on-remove-section
                           #(handlers/on-template-section-remove props)
                           :template-sections-form.action-buttons.messages/on-add-property
                           #(handlers/on-add-template-property props))
                    
                    disabled
                    (assoc :factories.input/disabled? true))]
    (factories.input/input-factory props metadata* section)))

;; Components
(defn- section-input
  "An input for a section."
  [{::keys [section]
    :template-sections-form/keys [inputs-metadatas]
    :or {inputs-metadatas [input/id input/name input/properties]}
    :as props}]
  [:div {:class [section-input-class]}
   (for [metadata inputs-metadatas]
     (render-input props metadata))])

(defn template-sections-form
  "A form for sections of a template"
  [{:template-sections-form/keys [sections inputs-metadatas] :as props}]

  {:pre [(spec/assert :template-sections-form/props props)]}
  
  [:div {:class base-class}
   [:span {:class label-class} "Sections"]
   [:div {:class inputs-container-class}
    (for [section sections
          :let [id (template-section/get-id section)]]
      ^{:key id}
      [section-input (assoc props :template-sections-form.impl/section section)])]])
