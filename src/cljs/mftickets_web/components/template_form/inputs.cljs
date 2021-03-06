(ns mftickets-web.components.template-form.inputs
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-sections-form :as components.template-sections-form]
            [mftickets-web.components.template-form.sections-actions-buttons :as sections-actions-buttons]
            [mftickets-web.components.factories.input :as factories.input])
  (:refer-clojure :exclude [name]))

;; Metadata
(def id
  {:factories.input/component-kw ::components.input/input
   
   :factories.input/id :id
   :factories.input/focus-value-fn :id
   :factories.input/update-value-fn #(assoc %1 :id %2)
   :factories.input/messages
   {:input.messages/on-change :template-form.handlers/on-input-change}

   :input/id :id
   :input/label "Id"
   :input/disabled true})

(def name
  {:factories.input/component-kw ::components.input/input

   :factories.input/id :name
   :factories.input/focus-value-fn :name
   :factories.input/update-value-fn #(assoc %1 :name %2)
   :factories.input/messages
   {:input.messages/on-change :template-form.handlers/on-input-change}

   :input/id :name
   :input/label "Name"})

(def project-id
  {:factories.input/component-kw ::components.input/input

   :factories.input/id :project-id
   :factories.input/focus-value-fn :project-id
   :factories.input/update-value-fn #(assoc %1 :project-id %2)
   :factories.input/messages
   {:input.messages/on-change :template-form.handlers/on-input-change}

   :input/id :project-id
   :input/label "Project Id"})

(def creation-date
  {:factories.input/component-kw ::components.input/input

   :factories.input/id :creation-date
   :factories.input/focus-value-fn :creation-date
   :factories.input/update-value-fn #(assoc %1 :creation-date %2)
   :factories.input/messages
   {:input.messages/on-change :template-form.handlers/on-input-change}

   :input/id :creation-date
   :input/label "Creation Date"
   :input/disabled true})

(def sections-actions-buttons
  {:factories.input/component-kw ::sections-actions-buttons/template-form-sections-actions-buttons
   :factories.input/id :sections-actions-buttons
   :factories.input/focus-value-fn #(do nil)
   :factories.input/update-value-fn #(do %1)
   :factories.input/messages
   {:template-form.sections-actions-buttons.messages/on-add-template-section
    :template-form.handlers/on-add-template-section}})

(def sections
  {:factories.input/component-kw ::components.template-sections-form/template-sections-form
   :factories.input/id :sections
   :factories.input/focus-value-fn :sections
   :factories.input/update-value-fn #(assoc %1 :sections %2)
   :factories.input/messages
   {:template-sections-form.messages/on-sections-change
    :template-form.handlers/on-input-change}

   :template-sections-form/properties-types
   (factories.input/->DynamicMetadata :template-form/properties-types)})
