(ns mftickets-web.components.project-form
  (:require
   [mftickets-web.components.form :as components.form]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.project-form.queries :as queries]
   [com.rpl.specter :as s]
   [mftickets-web.components.project-form.handlers :as handlers]
   [cljs.spec.alpha :as spec]
   [mftickets-web.components.project-form.inputs :as inputs]
   [mftickets-web.components.factories.input :as factories.input]))

;; Components
(defn- render-input
  "Renders an input from the current props and input metadata."
  [{:project-form/keys [edited-project] :as props} metadata]

  {:pre [(spec/assert :factories/input metadata)]}

  (let [handlers {:project-form.handlers/on-input-change
                  #(handlers/on-input-change props metadata %)}
        metadata* (assoc metadata :factories.input/handlers handlers)]

    (factories.input/input-factory metadata* edited-project)))

(defn- props->form-props
  "Prepares the props for the form component."
  [{:project-form/keys [form-props]
    :project-form.messages/keys [on-edited-project-submit]
    :as props}]
  {:pre [(fn? on-edited-project-submit)]}
  (assoc form-props :on-submit on-edited-project-submit))

(defn project-form
  "A form to create/edit/view a project."
  [{:project-form/keys [original-project edited-project inputs-metadata]
    :or {inputs-metadata [inputs/id inputs/name inputs/description]}
    :keys [state]
    :as props}]

  [components.form/form (props->form-props props)
   (for [input-metadata inputs-metadata]
     (render-input props input-metadata))])
