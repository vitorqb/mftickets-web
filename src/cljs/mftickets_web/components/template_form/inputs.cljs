(ns mftickets-web.components.template-form.inputs
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.template-sections-form :as components.template-sections-form])
  (:refer-clojure :exclude [name]))

;; Metadata
(def id
  {:factories.input/component #'components.input/input
   :factories.input/id :id
   :factories.input/focus-value-fn :id
   :factories.input/update-value-fn #(assoc %1 :id %2)
   :factories.input/events-mapping {:InputChange :OnChange->}
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :id
   :input/label "Id"
   :input/disabled true})

(def name
  {:factories.input/component #'components.input/input
   :factories.input/id :name
   :factories.input/focus-value-fn :name
   :factories.input/update-value-fn #(assoc %1 :name %2)
   :factories.input/events-mapping {:InputChange :OnChange->}
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :name
   :input/label "Name"})

(def project-id
  {:factories.input/component #'components.input/input
   :factories.input/id :project-id
   :factories.input/focus-value-fn :project-id
   :factories.input/update-value-fn #(assoc %1 :project-id %2)
   :factories.input/events-mapping {:InputChange :OnChange->}
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :project-id
   :input/label "Project Id"})

(def creation-date
  {:factories.input/component #'components.input/input
   :factories.input/id :creation-date
   :factories.input/focus-value-fn :creation-date
   :factories.input/update-value-fn #(assoc %1 :creation-date %2)
   :factories.input/events-mapping {:InputChange :OnChange->}
   :factories.input/assoc-value-to-props-fn #(assoc %1 :input/value %2)
   :input/id :creation-date
   :input/label "Creation Date"})

(def sections
  {:factories.input/component #'components.template-sections-form/template-sections-form
   :factories.input/id :sections
   :factories.input/focus-value-fn :sections
   :factories.input/update-value-fn #(assoc %1 :sections %2)
   :factories.input/events-mapping {:InputChange :OnChange->}
   :factories.input/assoc-value-to-props-fn #(assoc %1 :template-sections-form/sections %2)})
