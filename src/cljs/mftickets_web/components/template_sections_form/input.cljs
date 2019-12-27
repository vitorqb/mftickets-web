(ns mftickets-web.components.template-sections-form.input
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-properties-form :as components.template-properties-form]
            [mftickets-web.components.template-sections-form.actions-buttons :as actions-buttons]
            [mftickets-web.components.factories.input :as factories.input])
  (:refer-clojure :exclude [name]))

;; Metadata
(def id
  {:factories.input/component-kw ::components.input/input
   :factories.input/id :id
   :factories.input/focus-value-fn :id
   :factories.input/update-value-fn #(assoc %1 :id %2)
   :factories.input/messages
   {:input.messages/on-change :template-sections-form.handlers/on-template-section-input-change}

   :input/label "Id"
   :input/disabled true})

(def name
  {:factories.input/component-kw ::components.input/input
   :factories.input/id :name
   :factories.input/focus-value-fn :name
   :factories.input/update-value-fn #(assoc %1 :name %2)
   :factories.input/messages
   {:input.messages/on-change :template-sections-form.handlers/on-template-section-input-change}

   :input/label "Name"})

(def actions-buttons
  {:factories.input/component-kw ::actions-buttons/template-section-form-action-buttons
   :factories.input/id :actions-buttons
   :factories.input/focus-value-fn #(do nil)
   :factories.input/update-value-fn #(do nil)
   :factories.input/messages
   {:template-sections-form.action-buttons.messages/on-add-property
    :template-sections-form.handlers/on-add-template-property
    
    :template-sections-form.action-buttons.messages/on-remove-section
    :template-sections-form.handlers/on-template-section-remove

    :template-sections-form.action-buttons.messages/on-move-section-back
    :template-sections-form.handlers/on-template-section-move-back

    :template-sections-form.action-buttons.messages/on-move-section-forward
    :template-sections-form.handlers/on-template-section-move-forward}})

(def properties
  {:factories.input/component-kw ::components.template-properties-form/template-properties-form
   :factories.input/id :properties
   :factories.input/focus-value-fn :properties
   :factories.input/update-value-fn #(assoc %1 :properties %2)
   :factories.input/messages
   {:template-properties-form.messages/on-properties-change
    :template-sections-form.handlers/on-template-section-input-change}

   :template-properties-form/properties-types
   (factories.input/->DynamicMetadata :template-sections-form/properties-types)})
