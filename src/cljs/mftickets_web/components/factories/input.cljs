(ns mftickets-web.components.factories.input
  "Factories for inputs, providing a common API for constructing child components
  that are used to display part of the parent's object."
  (:require [cljs.spec.alpha :as spec]))

;; Specs
(spec/def :factories.input/component ifn?)
(spec/def :factories.input/component-kw keyword?)
(spec/def :factories.input/assoc-disabled? ifn?)
(spec/def :factories.input/disabled? (spec/or :boolean boolean? :nil nil?))
(spec/def :factories.input/id keyword)
(spec/def :factories.input/focus-value-fn ifn?)
(spec/def :factories.input/update-value-fn ifn?)
(spec/def :factories.input/assoc-value-to-props-fn ifn?)

(spec/def :factories/input
  (spec/and
   (spec/keys
    :req [:factories.input/component-kw
          :factories.input/id
          :factories.input/focus-value-fn
          :factories.input/update-value-fn])))

;; Multimethods
(defmulti input-factory-opts
  "Main registration for components that can behave like inputs.
  This method must return a map with:
  - `factories.input/component`: The component that will be used.
  - `factories.input/assoc-disabled?`: Receives props and a boolean value and set's the the
       component to be disabled.
  - `factories.input/assoc-value-to-props-fn`: A function that accepts a props that will be
       passed to the `component` and assocs the focused value to it."
  (fn [component-kw] component-kw))

;; Factories
(defn input-factory
  "Creates a new input component from parent props and input metadata.

  Accepts:
  - `metadata`: Metadata for the input being created.
  - `parent-value`: The current value being displayed.
  
  Recognizes the following keys in metadata:
  - `factories.input/component-kw`: The kw representing the component to render. Will be
       given to the `input-factory-opts` multimethod.
  - `factories.input/disabled?`: Whether input is disabled or not.
  - `factories.input/id`: An unique id, used as react key.
  - `factories.input/focus-value-fn`: A function that focus on an specific part of the parent
       value that will be passed to the child component.
  - `factories.input/update-value-fn`: A function accepting the old parent value and the new
       user input and updates it."
  [{:factories.input/keys [component-kw component id focus-value-fn assoc-value-to-props-fn
                           assoc-disabled? disabled?]
    :or {disabled? nil}
    :as metadata}
   parent-value]
  
  {:pre [(spec/assert :factories/input metadata)
         (do (spec/assert :factories.input/disabled? disabled?) true)]}

  (let [{:factories.input/keys [component assoc-value-to-props-fn assoc-disabled?]}
        (input-factory-opts component-kw)

        _ (spec/assert :factories.input/component component)
        _ (spec/assert :factories.input/assoc-disabled? assoc-disabled?)
        _ (spec/assert :factories.input/assoc-value-to-props-fn assoc-value-to-props-fn)
        
        value
        (focus-value-fn parent-value)

        props
        (cond-> metadata
          :always (assoc-value-to-props-fn value)
          (not (nil? disabled?)) (assoc-disabled? disabled?))]

    ^{:key id}
    [component props]))


