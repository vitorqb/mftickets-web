(ns mftickets-web.components.template-properties-form.input
  (:require [mftickets-web.components.input :as components.input]
            [mftickets-web.components.select :as components.select]
            [mftickets-web.domain.boolean :as domain.boolean]
            [mftickets-web.domain.select :as domain.select]
            [mftickets-web.components.template-properties-form.actions-buttons :as actions-buttons])
  (:refer-clojure :exclude [name]))

(def id
  {:factories.input/component #'components.input/input
   :factories.input/assoc-disabled? #(assoc %1 :input/disabled %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)

   :factories.input/id :id
   :factories.input/focus-value-fn :id
   :factories.input/update-value-fn #(assoc %1 :id %2)

   :input/label "Id"
   :input/disabled true})

(def name
  {:factories.input/component #'components.input/input
   :factories.input/assoc-disabled? #(assoc %1 :input/disabled %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)

   :factories.input/id :name
   :factories.input/focus-value-fn :name
   :factories.input/update-value-fn #(assoc %1 :name %2)
   
   :input/label "Name"})

(def is-multiple
  {:factories.input/component #'components.select/select
   :factories.input/assoc-disabled? #(assoc %1 :select/disabled %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :select/value %2)

   :factories.input/id :is-multiple
   :factories.input/focus-value-fn #(some-> % :is-multiple domain.select/boolean->option)
   :factories.input/update-value-fn (fn [property is-multiple-option]
                                      (->> is-multiple-option
                                           domain.select/option->boolean
                                           (assoc property :is-multiple)))

   :select/options domain.select/boolean-options
   :select/label "Is Multiple?"
   :select/contents-wrapper-class components.input/base-html-input-class
   :select/label-wrapper-class components.input/base-input-wrapper-label-class})

(def value-type
  {:factories.input/component #'components.input/input
   :factories.input/assoc-disabled? #(assoc %1 :input/disabled %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   
   :factories.input/id :value-type
   :factories.input/focus-value-fn :value-type
   :factories.input/update-value-fn #(assoc %1 :value-type %2)

   :input/label "Type"})

(def actions-buttons
  {:factories.input/component #'actions-buttons/template-properties-form-actions-buttons
   :factories.input/assoc-disabled? #(do %1)
   :factories.input/assoc-value-to-props-fn #(do %1)

   :factories.input/id :actions-buttons
   :factories.input/focus-value-fn #(do nil)
   :factories.input/update-value-fn #(do nil)})
