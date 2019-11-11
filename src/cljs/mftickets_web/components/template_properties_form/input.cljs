(ns mftickets-web.components.template-properties-form.input
  (:require [mftickets-web.components.input :as components.input])
  (:refer-clojure :exclude [name]))

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

(def is-multiple
  {:factories.input/component #'components.input/input
   :factories.input/id :is-multiple
   :factories.input/focus-value-fn #(-> % :is-multiple str)
   :factories.input/update-value-fn #(assoc %1 :is-multiple %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/label "Multiple?"})

(def value-type
  {:factories.input/component #'components.input/input
   :factories.input/id :value-type
   :factories.input/focus-value-fn :value-type
   :factories.input/update-value-fn #(assoc %1 :value-type %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/label "Type"})
