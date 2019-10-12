(ns mftickets-web.components.project-form
  (:require
   [mftickets-web.components.form :as components.form]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.project-form.queries :as queries]
   [com.rpl.specter :as s]
   [mftickets-web.components.project-form.handlers :as handlers]
   [mftickets-web.events :as events]
   [cljs.spec.alpha :as spec]))

;; Specs
(spec/def :project-form.input-metadata/id
  keyword?)

(spec/def :project-form.input-metadata/label
  string?)

(spec/def :project-form.input-metadata/disabled
  (spec/nilable boolean?))

(spec/def :project-form.input-metadata/path
  (spec/or :keyword keyword? :seq-of-keyword (spec/coll-of keyword?)))

(spec/def :project-form/input-metadata
  (spec/keys :req-un [:project-form.input-metadata/id
                      :project-form.input-metadata/label
                      :project-form.input-metadata/path]
             :opt-un [:project-form.input-metadata/disabled]))

;; Input metadata
(def id-input-metadata
  {:id :id
   :label "Id"
   :path :id
   :disabled true})

(def name-input-metadata
  {:id :name
   :label "Name"
   :path :name})

(def description-input-metadata
  {:id :description
   :label "Description"
   :path :description})

;; Components
(defn- render-input
  "Renders a input from metadata given the edited project and a metadata."
  [{:project-form/keys [edited-project] :as props}
   {:keys [id label path disabled] :as input-metadata}]
  {:pre [(spec/valid? :project-form/input-metadata input-metadata)]}
  
  (let [value (or (s/select-first path edited-project) "")
        on-change-> #(handlers/on-input-change props {:input-path path :input-value %})]

    ^{:key id}
    [components.input/input {:label label
                             :disabled disabled
                             :value value
                             :events {:on-change-> on-change->}
                             :parent-props props}]))

(defn- props->form-props
  "Prepares the props for the form component."
  [{:project-form/keys [form-props] :as props}]
  {:pre [(-> form-props (contains? :on-submit) not)]}
  (let [on-submit #(events/react! props (handlers/on-submit props))]
    (assoc form-props :on-submit on-submit)))

(defn project-form
  "A form to create/edit/view a project."
  [{:project-form/keys [original-project edited-project inputs-metadata]
    :or {inputs-metadata [id-input-metadata name-input-metadata description-input-metadata]}
    :keys [state]
    :as props}]
  {:pre [(spec/valid? (spec/coll-of :project-form/input-metadata) inputs-metadata)]}

  [components.form/form (props->form-props props)
   (for [input-metadata inputs-metadata]
     (render-input props input-metadata))])
