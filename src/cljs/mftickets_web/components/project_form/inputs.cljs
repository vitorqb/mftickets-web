(ns mftickets-web.components.project-form.inputs
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input])
  (:refer-clojure :exclude [name]))

;; Metadata
(def id
  {:factories.input/component-kw ::components.input/input
   
   :factories.input/id :id
   :factories.input/focus-value-fn :id
   :factories.input/update-value-fn #(assoc %1 :id %2)

   :input/id :id
   :input/label "Id"
   :input/disabled true})

(def name
  {:factories.input/component-kw ::components.input/input

   :factories.input/id :name
   :factories.input/focus-value-fn :name
   :factories.input/update-value-fn #(assoc %1 :name %2)

   :input/id :name
   :input/label "Name"})

(def description
  {:factories.input/component-kw ::components.input/input

   :factories.input/id :description
   :factories.input/focus-value-fn :description
   :factories.input/update-value-fn #(assoc %1 :description %2)

   :input/id :description
   :input/label "Description"})
