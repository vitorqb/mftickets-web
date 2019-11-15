(ns mftickets-web.components.template-sections-form
  (:require [mftickets-web.components.input :as components.input]
            [cljs.spec.alpha :as spec]
            [mftickets-web.components.template-sections-form.input :as input]
            [mftickets-web.components.factories.input :as factories.input]))

;; Scss
(def base-class "template-sections-form")
(def label-class (str base-class "__label"))
(def inputs-container-class (str base-class "__inputs-container"))
(def section-input-class (str base-class "__section-input"))

;; Specs
(spec/def :template-sections-form/sections (spec/coll-of any?))
(spec/def :template-sections-form/inputs-metadatas any?)

(spec/def :template-sections-form/props
  (spec/keys
   :req [:template-sections-form/sections]))

;; Helpers
(defn- render-input
  "Renders an input from it's metadata."
  [{::keys [section] :template-sections-form/keys [disabled] :as props} metadata]

  {:pre [(spec/assert :factories/input metadata)]}

  ;; !!!! TODO -> Smarter way to set disabled without knowing the component
  (let [metadata* (cond-> metadata disabled (assoc :factories.input/disabled? true))]
    (factories.input/input-factory props metadata* section)))

;; Components
(defn- section-input
  "An input for a section."
  [{::keys [section]
    :template-sections-form/keys [inputs-metadatas]
    :or {inputs-metadatas [input/id input/name input/properties]}
    :as props}]
  [:div {:class [section-input-class]}
   (for [metadata inputs-metadatas]
     (render-input props metadata))])

(defn template-sections-form
  "A form for sections of a template"
  [{:template-sections-form/keys [sections inputs-metadatas] :as props}]

  {:pre [(spec/assert :template-sections-form/props props)]}
  
  [:div {:class base-class}
   [:span {:class label-class} "Sections"]
   [:div {:class inputs-container-class}
    (for [{:keys [id] :as section} sections]
      ^{:key id}
      [section-input (assoc props ::section section)])]])
