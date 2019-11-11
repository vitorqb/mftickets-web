(ns mftickets-web.components.project-form
  (:require
   [mftickets-web.components.form :as components.form]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.project-form.queries :as queries]
   [com.rpl.specter :as s]
   [mftickets-web.components.project-form.handlers :as handlers]
   [mftickets-web.events :as events]
   [cljs.spec.alpha :as spec]
   [mftickets-web.components.project-form.inputs :as inputs]
   [mftickets-web.components.factories.input :as factories.input]))

;; Components
(defn- render-input
  "Renders an input from the current props and input metadata."
  [{:project-form/keys [edited-project] :as props}
   {:project-form.input/keys [events-mapping] :as metadata}]

  {:pre [(spec/assert :factories/input metadata)
         (spec/assert :project-form.input/events-mapping events-mapping)]}

  (let [InputChange #(handlers/->InputChange props metadata %)
        metadata* (update metadata :events assoc (:InputChange events-mapping) InputChange)]

    (factories.input/input-factory props metadata* edited-project)))

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

  [components.form/form (props->form-props props)
   (for [input-metadata inputs-metadata]
     (render-input props input-metadata))])
