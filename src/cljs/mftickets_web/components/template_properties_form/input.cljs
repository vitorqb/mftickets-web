(ns mftickets-web.components.template-properties-form.input
  (:require [mftickets-web.components.input :as components.input]
            [mftickets-web.components.select :as components.select]
            [mftickets-web.domain.boolean :as domain.boolean]
            [mftickets-web.domain.select :as domain.select]
            [mftickets-web.components.template-properties-form.actions-buttons :as actions-buttons]
            [mftickets-web.components.factories.input :as factories.input])
  (:refer-clojure :exclude [name]))

(def id
  {:factories.input/component-kw ::components.input/input
   :factories.input/id :id
   :factories.input/focus-value-fn :id
   :factories.input/update-value-fn #(assoc %1 :id %2)
   :factories.input/messages
   {:input.messages/on-change :template-properties-form.handlers/on-change}

   :input/label "Id"
   :input/disabled true})

(def name
  {:factories.input/component-kw ::components.input/input
   :factories.input/id :name
   :factories.input/focus-value-fn :name
   :factories.input/update-value-fn #(assoc %1 :name %2)
   :factories.input/messages
   {:input.messages/on-change :template-properties-form.handlers/on-change}
   
   :input/label "Name"})

(def is-multiple
  {:factories.input/component-kw ::components.select/select
   :factories.input/id :is-multiple
   :factories.input/focus-value-fn #(some-> % :is-multiple domain.select/boolean->option)
   :factories.input/update-value-fn (fn [property is-multiple-option]
                                      (->> is-multiple-option
                                           domain.select/option->boolean
                                           (assoc property :is-multiple)))
   :factories.input/messages
   {:select.messages/on-select-change :template-properties-form.handlers/on-change}

   :select/options domain.select/boolean-options
   :select/label "Is Multiple?"
   :select/contents-wrapper-class components.input/base-html-input-class
   :select/label-wrapper-class components.input/base-input-wrapper-label-class})

(def value-type
  {:factories.input/component-kw ::components.select/select
   :factories.input/id :value-type
   :factories.input/focus-value-fn #(-> % :value-type domain.select/keyword->option)
   :factories.input/update-value-fn #(assoc %1 :value-type (domain.select/option->keyword %2))
   :factories.input/messages
   {:select.messages/on-select-change :template-properties-form.handlers/on-change}

   :select/options
   (factories.input/->DynamicMetadata
    #(map domain.select/keyword->option (:template-properties-form/properties-types %)))
   :select/label "Value Type"
   :select/contents-wrapper-class components.input/base-html-input-class
   :select/label-wrapper-class components.input/base-input-wrapper-label-class})

(def actions-buttons
  {:factories.input/component-kw ::actions-buttons/template-properties-form-actions-buttons
   :factories.input/id :actions-buttons
   :factories.input/focus-value-fn #(do nil)
   :factories.input/update-value-fn #(do nil)
   :factories.input/messages
   {:template-properties-form.actions-buttons.messages/on-remove-property
    :template-properties-form.handlers/on-remove}})
