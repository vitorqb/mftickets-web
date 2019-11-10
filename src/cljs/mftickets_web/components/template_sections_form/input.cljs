(ns mftickets-web.components.template-sections-form.input
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input])
  (:refer-clojure :exclude [name]))


;; Specs
(spec/def :template-sections-form.input/component ifn?)
(spec/def :template-sections-form.input/id keyword)
(spec/def :template-sections-form.input/query-section-value-fn ifn?)
(spec/def :template-sections-form.input/assoc-section-value-fn ifn?)
(spec/def :template-sections-form.input/events-mapping #(satisfies? IAssociative %))
(spec/def :template-sections-form.input/assoc-value-to-props-fn ifn?)

(spec/def :template-sections-form/input
  (spec/keys
   :req [:template-sections-form.input/component
         :template-sections-form.input/id
         :template-sections-form.input/query-section-value-fn
         :template-sections-form.input/assoc-section-value-fn
         :template-sections-form.input/events-mapping
         :template-sections-form.input/assoc-value-to-props-fn]))

;; Metadata
(def id
  {:template-sections-form.input/component #'components.input/input
   :template-sections-form.input/id :id
   :template-sections-form.input/query-section-value-fn :id
   :template-sections-form.input/assoc-section-value-fn #(assoc %1 :id %2)
   :template-sections-form.input/events-mapping {}
   :template-sections-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/label "Id"
   :input/disabled true})

(def name
  {:template-sections-form.input/component #'components.input/input
   :template-sections-form.input/id :name
   :template-sections-form.input/query-section-value-fn :name
   :template-sections-form.input/assoc-section-value-fn #(assoc %1 :name %2)
   :template-sections-form.input/events-mapping {}
   :template-sections-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/label "Name"})
