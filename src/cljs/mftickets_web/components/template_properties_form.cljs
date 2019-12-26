(ns mftickets-web.components.template-properties-form
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.factories.input :as factories.input]
            [mftickets-web.components.template-properties-form.input :as input]
            [mftickets-web.components.template-properties-form.handlers :as handlers]
            [mftickets-web.domain.template-property :as domain.template-property]
            [mftickets-web.components.factories.input :as factories.input]))

;; Css
(def base-class "template-properties-form")
(def label-class (str base-class "__label"))
(def inputs-container-class (str base-class "__inputs-container"))
(def property-input-class (str base-class "__property-input"))

;; Specs
(spec/def :template-properties-form.property/id (spec/or :nil nil? :int int?))
(spec/def :template-properties-form.property/name string?)
(spec/def :template-properties-form.property/is-multiple boolean?)
(spec/def :template-properties-form.property/value-type keyword?)

(spec/def :template-properties-form/property
  (spec/keys
   :req-un [:template-properties-form.property/id
            :template-properties-form.property/name
            :template-properties-form.property/is-multiple
            :template-properties-form.property/value-type]))

(spec/def :template-properties-form/properties
  (spec/coll-of :template-properties-form/property))

(spec/def :template-properties-form/disabled (spec/nilable boolean?))

(spec/def :template-properties-form/props
  (spec/keys
   :req [:template-properties-form/properties]))

(spec/def :template-properties-form.handlers/on-change ifn?)
(spec/def :template-properties-form.handlers/on-remove ifn?)
(spec/def :template-properties-form.handlers/on-move-back ifn?)
(spec/def :template-properties-form.handlers/on-move-forward ifn?)
(spec/def :template-properties-form/handlers
  (spec/keys
   :req [:template-properties-form.handlers/on-change
         :template-properties-form.handlers/on-remove
         :template-properties-form.handlers/on-move-back
         :template-properties-form.handlers/on-move-forward]))

;; Components
(defn- property-input-handlers
  "Map of known handlers for property inputs."
  [props metadata]

  {:post [(spec/assert :template-properties-form/handlers %)]}

  {:template-properties-form.handlers/on-change
   #(handlers/on-template-property-change props metadata %)

   :template-properties-form.handlers/on-remove
   #(handlers/on-remove-template-property props)

   :template-properties-form.handlers/on-move-back
   #(handlers/on-move-template-property-back props)

   :template-properties-form.handlers/on-move-forward
   #(handlers/on-move-template-property-forward props)})

(defn property-input
  "An input for a single template property."
  [{:template-properties-form/keys [inputs-metadatas disabled]
    :template-properties-form.impl/keys [property]
    :or {inputs-metadatas [input/id input/name input/is-multiple input/value-type]}
    :as props}]

  {:pre [(spec/assert (spec/coll-of :factories/input) inputs-metadatas)]}
  
  [:div {:class property-input-class}
   (for [metadata inputs-metadatas
         :let [handlers
               (property-input-handlers props metadata)
               
               context
               (select-keys props [:template-properties-form/properties-types])

               metadata*
               (cond-> metadata
                 :always (assoc :factories.input/handlers handlers)
                 :always (assoc :factories.input/parent-context context)
                 disabled (assoc :factories.input/disabled? true))]]
     
     (factories.input/input-factory metadata* property))])

(defn template-properties-form
  "A form to render template properties."
  [{:template-properties-form/keys [properties] :as props}]

  {:pre [(spec/assert :template-properties-form/props props)]}

  [:div {:class base-class}
   [:span {:class label-class} "Properties"]
   [:div {:class inputs-container-class}
    (for [property (sort-by :order properties)
          :let [props* (assoc props :template-properties-form.impl/property property)
                id (domain.template-property/get-id property)]]
      ^{:key id}
      [property-input props*])]])

(defmethod factories.input/input-factory-opts ::template-properties-form [_]
  {:factories.input/component template-properties-form
   :factories.input/assoc-disabled? #(assoc %1 :template-properties-form/disabled %2)
   :factories.input/assoc-value-to-props-fn #(assoc %1 :template-properties-form/properties %2)})
