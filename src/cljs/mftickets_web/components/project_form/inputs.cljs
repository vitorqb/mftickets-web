(ns mftickets-web.components.project-form.inputs
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input])
  (:refer-clojure :exclude [name]))


;; Spec
(spec/def :project-form.input/component ifn?)
(spec/def :project-form.input/id keyword)
(spec/def :project-form.input/query-project-value-fn ifn?)
(spec/def :project-form.input/assoc-project-value-fn ifn?)
(spec/def :project-form.input/events-mapping #(satisfies? IAssociative %))
(spec/def :project-form.input/assoc-value-to-props-fn ifn?)

(spec/def :project-form/input
  (spec/keys
   :req [:project-form.input/component
         :project-form.input/id
         :project-form.input/query-project-value-fn
         :project-form.input/assoc-project-value-fn
         :project-form.input/events-mapping
         :project-form.input/assoc-value-to-props-fn]))

;; Metadata
(def id
  {:project-form.input/component #'components.input/input
   :project-form.input/id :id
   :project-form.input/query-project-value-fn :id
   :project-form.input/assoc-project-value-fn #(assoc %1 :id %2)
   :project-form.input/events-mapping {:InputChange :OnChange->}
   :project-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :id
   :input/label "Id"
   :input/disabled true})

(def name
  {:project-form.input/component #'components.input/input
   :project-form.input/id :name
   :project-form.input/query-project-value-fn :name
   :project-form.input/assoc-project-value-fn #(assoc %1 :name %2)
   :project-form.input/events-mapping {:InputChange :OnChange->}
   :project-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :name
   :input/label "Name"})

(def description
  {:project-form.input/component #'components.input/input
   :project-form.input/id :description
   :project-form.input/query-project-value-fn :description
   :project-form.input/assoc-project-value-fn #(assoc %1 :description %2)
   :project-form.input/events-mapping {:InputChange :OnChange->}
   :project-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :description
   :input/label "Description"})
