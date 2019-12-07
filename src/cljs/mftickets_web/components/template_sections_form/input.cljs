(ns mftickets-web.components.template-sections-form.input
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-properties-form :as components.template-properties-form]
            [mftickets-web.components.template-sections-form.actions-buttons :as actions-buttons])
  (:refer-clojure :exclude [name]))

;; Metadata
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

(def actions-buttons
  {:factories.input/component #'actions-buttons/template-section-form-action-buttons
   :factories.input/assoc-disabled? #(do %1)
   :factories.input/assoc-value-to-props-fn #(do %1)

   :factories.input/id :actions-buttons
   :factories.input/focus-value-fn #(do nil)
   :factories.input/update-value-fn #(do nil)})

(def properties
  {:factories.input/component #'components.template-properties-form/template-properties-form
   :factories.input/assoc-disabled? #(assoc %1 :template-properties-form/disabled %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :template-properties-form/properties %2)

   :factories.input/id :properties
   :factories.input/focus-value-fn :properties
   :factories.input/update-value-fn #(assoc %1 :properties %2)})
