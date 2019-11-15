(ns mftickets-web.components.template-properties-form
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.factories.input :as factories.input]
            [mftickets-web.components.template-properties-form.input :as input]))

;; Css
(def base-class "template-properties-form")
(def label-class (str base-class "__label"))
(def inputs-container-class (str base-class "__inputs-container"))
(def property-input-class (str base-class "__property-input"))

;; Specs
(spec/def :template-properties-form.property/id int?)
(spec/def :template-properties-form.property/name string?)
(spec/def :template-properties-form.property/is-multiple boolean?)
(spec/def :template-properties-form.property/value-type string?)

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

;; Components
(defn property-input
  "An input for a single template property."
  [{:template-properties-form/keys [inputs-metadatas disabled]
    ::keys [property]
    :or {inputs-metadatas [input/id input/name input/is-multiple input/value-type]}
    :as props}]

  {:pre [(spec/assert (spec/coll-of :factories/input) inputs-metadatas)]}
  
  [:div {:class property-input-class}
   (for [metadata inputs-metadatas
         :let [metadata* (cond-> metadata disabled (assoc :factories.input/disabled? true))]]
     (factories.input/input-factory props metadata* property))])

(defn template-properties-form
  "A form to render template properties."
  [{:template-properties-form/keys [properties] :as props}]

  {:pre [(spec/assert :template-properties-form/props props)]}

  [:div {:class base-class}
   [:span {:class label-class} "Properties"]
   [:div {:class inputs-container-class}
    (for [{:keys [id] :as property} properties
          :let [props* (assoc props ::property property)]]
      ^{:key id}
      [property-input props*])]])
