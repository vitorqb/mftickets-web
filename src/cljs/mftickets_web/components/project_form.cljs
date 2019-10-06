(ns mftickets-web.components.project-form
  (:require
   [mftickets-web.components.form :as components.form]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.project-form.queries :as queries]
   [com.rpl.specter :as s]
   [mftickets-web.components.project-form.handlers :as handlers]
   [mftickets-web.events :as events]))

(def ^:private inputs-metadata
  "Metadata for the inputs of the form."
  [{:id :id
    :label "Id"
    :path :id
    :disabled true}
   {:id :name
    :label "Name"
    :path :name}
   {:id :description
    :label "Description"
    :path :description}])

(defn- render-input
  "Renders a input from metadata given the edited project and a metadata."
  [{:project-form/keys [edited-project] :as props}
   {:keys [id label path disabled]}]

  (let [value (or (s/select-first path edited-project) "")
        on-change-> #(handlers/on-input-change props {:input-path path :input-value %})]

    ^{:key id}
    [components.input/input {:label label
                             :disabled disabled
                             :value value
                             :events {:on-change-> on-change->}
                             :parent-props props}]))

(defn project-form
  "A form to edit/view a project."
  [{:project-form/keys [original-project edited-project]
    :keys [state]
    :as props}]

  [components.form/form
   {:on-submit #(events/react! props (handlers/on-submit props))}
   (for [input-metadata inputs-metadata]
     (render-input props input-metadata))])
