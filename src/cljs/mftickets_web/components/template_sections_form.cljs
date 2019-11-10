(ns mftickets-web.components.template-sections-form
  (:require [mftickets-web.components.input :as components.input]
            [cljs.spec.alpha :as spec]
            [mftickets-web.components.template-sections-form.input :as input]))

;; Scss
(def base-class "template-sections-form")
(def label-class (str base-class "__label"))
(def inputs-container-class (str base-class "__inputs-container"))

;; Specs
(spec/def :template-sections-form/sections (spec/coll-of any?))
(spec/def :template-sections-form/inputs-metadatas any?)

(spec/def :template-sections-form/props
  (spec/keys
   :req [:template-sections-form/sections]))

;; Helpers
(defn- render-input
  "Renders an input from it's metadata."
  [{::keys [section] :template-sections-form/keys [disabled] :as props}
   {:template-sections-form.input/keys [component id query-section-value-fn
                                        assoc-value-to-props-fn]
    :as metadata}]

  {:pre [(spec/assert :template-sections-form/input metadata)]}
  
  (let [value (query-section-value-fn section)
        props* (cond-> metadata
                 :always (assoc-value-to-props-fn value)
                 disabled (assoc :input/disabled true))]

    ^{:key id}
    [component props*]))

;; Components
(defn- section-input
  "An input for a section."
  [{::keys [section]
    :template-sections-form/keys [inputs-metadatas]
    :or {inputs-metadatas [input/id input/name]}
    :as props}]
  [:div
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
