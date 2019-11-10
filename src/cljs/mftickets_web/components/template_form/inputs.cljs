(ns mftickets-web.components.template-form.inputs
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-sections-form :as components.template-sections-form])
  (:refer-clojure :exclude [name]))

;; Specs
(spec/def :template-form.input/component ifn?)
(spec/def :template-form.input/id keyword)
(spec/def :template-form.input/query-template-value-fn ifn?)
(spec/def :template-form.input/assoc-template-value-fn ifn?)
(spec/def :template-form.input/events-mapping #(satisfies? IAssociative %))
(spec/def :template-form.input/assoc-value-to-props-fn ifn?)

(spec/def :template-form/input
  (spec/keys
   :req [:template-form.input/component
         :template-form.input/id
         :template-form.input/query-template-value-fn
         :template-form.input/assoc-template-value-fn
         :template-form.input/events-mapping
         :template-form.input/assoc-value-to-props-fn]))

;; Metadata
(def id
  {:template-form.input/component #'components.input/input
   :template-form.input/id :id
   :template-form.input/query-template-value-fn :id
   :template-form.input/assoc-template-value-fn #(assoc %1 :id %2)
   :template-form.input/events-mapping {:InputChange :OnChange->}
   :template-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :id
   :input/label "Id"
   :input/disabled true})

(def name
  {:template-form.input/component #'components.input/input
   :template-form.input/id :name
   :template-form.input/query-template-value-fn :name
   :template-form.input/assoc-template-value-fn #(assoc %1 :name %2)
   :template-form.input/events-mapping {:InputChange :OnChange->}
   :template-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :name
   :input/label "Name"})

(def project-id
  {:template-form.input/component #'components.input/input
   :template-form.input/id :project-id
   :template-form.input/query-template-value-fn :project-id
   :template-form.input/assoc-template-value-fn #(assoc %1 :project-id %2)
   :template-form.input/events-mapping {:InputChange :OnChange->}
   :template-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :project-id
   :input/label "Project Id"})

(def creation-date
  {:template-form.input/component #'components.input/input
   :template-form.input/id :creation-date
   :template-form.input/query-template-value-fn :creation-date
   :template-form.input/assoc-template-value-fn #(assoc %1 :creation-date %2)
   :template-form.input/events-mapping {:InputChange :OnChange->}
   :template-form.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :creation-date
   :input/label "Creation Date"})

(def sections
  {:template-form.input/component #'components.template-sections-form/template-sections-form
   :template-form.input/id :sections
   :template-form.input/query-template-value-fn :sections
   :template-form.input/assoc-template-value-fn #(assoc %1 :sections %2)
   :template-form.input/events-mapping {:InputChange :OnChange->}
   :template-form.input/assoc-value-to-props-fn #(assoc %1 :template-sections-form/sections %2)})
