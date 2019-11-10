(ns mftickets-web.components.template-sections-form.input
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-properties-form :as components.template-properties-from])
  (:refer-clojure :exclude [name]))

;; Metadata
(def id
  {:factories.input/component #'components.input/input
   :factories.input/id :id
   :factories.input/focus-value-fn :id
   :factories.input/update-value-fn #(assoc %1 :id %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/label "Id"
   :input/disabled true})

(def name
  {:factories.input/component #'components.input/input
   :factories.input/id :name
   :factories.input/focus-value-fn :name
   :factories.input/update-value-fn #(assoc %1 :name %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/label "Name"})

(def properties
  {:factories.input/component #'components.template-properties-from/template-properties-form
   :factories.input/id :properties
   :factories.input/focus-value-fn :properties
   :factories.input/update-value-fn #(assoc %1 :properties %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :template-properties-form/properties %2)})
