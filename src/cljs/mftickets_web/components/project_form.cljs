(ns mftickets-web.components.project-form
  (:require
   [mftickets-web.components.form :as components.form]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.project-form.queries :as queries]
   [com.rpl.specter :as s]
   [mftickets-web.components.project-form.handlers :as handlers]
   [mftickets-web.events :as events]
   [cljs.spec.alpha :as spec]
   [mftickets-web.components.project-form.inputs :as inputs]))

;; Components
(defn- render-input
  "Renders an input from the current props and input metadata."
  [{:project-form/keys [edited-project] :as props}
   {:project-form.input/keys [query-project-value-fn events-mapping value-kw component id
                              assoc-value-to-props-fn]
    :keys [events]
    :as metadata}]

  {:pre [(spec/assert :project-form/input metadata)]}

  (let [value (query-project-value-fn edited-project)
        InputChange #(handlers/->InputChange props metadata %)
        events* (assoc events (:InputChange events-mapping) InputChange)
        props* (-> metadata
                   (assoc :events events* :parent-props props)
                   (assoc-value-to-props-fn value))]

    ^{:key id}
    [component props*]))

(defn- props->form-props
  "Prepares the props for the form component."
  [{:project-form/keys [form-props] :as props}]
  {:pre [(-> form-props (contains? :on-submit) not)]}
  (let [on-submit #(events/react! props (handlers/->Submit props))]
    (assoc form-props :on-submit on-submit)))

(defn project-form
  "A form to create/edit/view a project."
  [{:project-form/keys [original-project edited-project inputs-metadata]
    :or {inputs-metadata [inputs/id inputs/name inputs/description]}
    :keys [state]
    :as props}]
  
  {:pre [(spec/assert (spec/coll-of :project-form/input) inputs-metadata)]}

  [components.form/form (props->form-props props)
   (for [input-metadata inputs-metadata]
     (render-input props input-metadata))])
