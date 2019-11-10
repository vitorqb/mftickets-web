(ns mftickets-web.components.template-sections-form.input
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input])
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
