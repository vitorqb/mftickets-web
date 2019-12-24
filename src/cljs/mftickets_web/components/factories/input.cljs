(ns mftickets-web.components.factories.input
  "Factories for inputs, providing a common API for constructing child components
  that are used to display part of the parent's object."
  (:require [cljs.spec.alpha :as spec]
            [com.rpl.specter :as s]))

;; Specs
(spec/def :factories.input/component ifn?)
(spec/def :factories.input/component-kw keyword?)
(spec/def :factories.input/assoc-disabled? ifn?)
(spec/def :factories.input/disabled? (spec/or :boolean boolean? :nil nil?))
(spec/def :factories.input/id keyword)
(spec/def :factories.input/focus-value-fn ifn?)
(spec/def :factories.input/update-value-fn ifn?)
(spec/def :factories.input/assoc-value-to-props-fn ifn?)
(spec/def :factories.input/handlers (spec/map-of keyword? ifn?))
(spec/def :factories.input/messages (spec/map-of keyword? keyword?))
(spec/def :factories.input/parent-context (spec/nilable map?))

(spec/def :factories/input
  (spec/and
   (spec/keys
    :opt [:factories.input/handlers
          :factories.input/messages]
    :req [:factories.input/component-kw
          :factories.input/id
          :factories.input/focus-value-fn
          :factories.input/update-value-fn])))

;; Types
(deftype DynamicMetadata [metadata-fn]
  ;; DynamicMetadata represents a metadata that needs to be calculated depending on the
  ;; parent's context.
  IFn
  (-invoke [_ parent-context] (metadata-fn parent-context)))

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

;; Helpers
(defn- assoc-messages
  "Given `factories.input/handlers` and `factories.input/messages` maps, assocs to the
  props the right handler for each message."
  [{:factories.input/keys [handlers messages] :as metadata}]
  (reduce
   (fn [metadata* [message-kw handler-kw]]
     (if-let [handler (get handlers handler-kw)]
       (assoc metadata* message-kw handler)
       (js/console.warn (str "NO HANDLER FOR KEY " handler-kw))))
   metadata
   messages))

(defn- dissoc-metadata
  "Dissocs from m all keys that are used for metadata (factories.input)"
  [m]
  (s/setval [s/MAP-KEYS #(= (namespace %) "factories.input")] s/NONE m))

(defn- calculate-all-dynamic-metadata
  "Given a map of metadata, calculates all dynamic metadata."
  [metadata parent-context]
  (s/transform [s/MAP-VALS #(instance? DynamicMetadata %)] #(%1 parent-context) metadata))

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
       user input and updates it.
  - `factories.input/handlers`: A map of handler-kw -> handler-fn, with functions responsible
       to handle all known messages for the component.
  - `factories.input/messages`: A map of message-kw -> handler-kw, mapping the input's expected
       message to the parent known handlers in `handlers`.
  - `factories.input/parent-context`: A map available for dynamic metadata. Each dynaic metadata
       has a `metadata-fn` which will receive the `parent-context` to produce the metadata."
  [{:factories.input/keys [component-kw component id focus-value-fn assoc-value-to-props-fn
                           assoc-disabled? disabled? handlers messages parent-context]
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
          :always (calculate-all-dynamic-metadata parent-context)
          :always (assoc-value-to-props-fn value)
          :always assoc-messages
          (not (nil? disabled?)) (assoc-disabled? disabled?)
          :always (dissoc-metadata))]

    ^{:key id}
    [component props]))


