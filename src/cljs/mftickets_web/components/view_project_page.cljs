(ns mftickets-web.components.view-project-page
  (:require [mftickets-web.components.project-picker :as components.project-picker]
            [mftickets-web.components.view-project-page.queries :as queries]
            [mftickets-web.components.view-project-page.handlers :as handlers]
            [mftickets-web.events :as events]
            [mftickets-web.components.project-form :as components.project-form]
            [com.rpl.specter :as s]
            [mftickets-web.components.project-form.inputs :as components.project-form.inputs]))

(def ^:private base-class "view-project-page")
(def ^:private project-picker-wrapper-class (str base-class "__project-picker-wrapper"))
(def ^:private project-picker-contents-class (str base-class "__project-picker-contents"))

(defn project-picker
  "Wrapper around project-picker. Allows the user to pick a project."
  [{:view-project-page/keys [projects]
    :keys [state]
    :as props}]

  (let [props {:project-picker/projects (or projects [])
               :project-picker/picked-project (queries/picked-project @state)
               :project-picker.messages/on-picked-project-change
               #(handlers/on-picked-project-change props %)}]

    [:div {:class [project-picker-wrapper-class]}
     [:span.featured-label-1 "Pick a project:"]
     [:div {:class [project-picker-contents-class]}
      [components.project-picker/project-picker props]]]))

(defn- project-display-form-inputs-metadata
  "Returns the metadata for the inputs of the project form."
  []
  [(assoc components.project-form.inputs/id :input/disabled true)
   (assoc components.project-form.inputs/name :input/disabled true)
   (assoc components.project-form.inputs/description :input/disabled true)])

(defn project-display-form
  "Wrapper around project-form used for displaying it."
  [{:keys [state] :as props}]
  (if-let [picked-project (queries/picked-project @state)]
    (let [Submit #(->> (handlers/->DeletePickedProject props) (events/react! props))
          inputs-metadata (project-display-form-inputs-metadata)
          props {:project-form/edited-project picked-project
                 :project-form/inputs-metadata inputs-metadata
                 :project-form/form-props {:button-style :danger :button-text "Delete!"}
                 :events {:Submit-> Submit}}]
      [components.project-form/project-form props])))

(defn view-project-page
  "A page component to view (and delete) a project."
  [props]
  [:div {:class [base-class]}
   [:h3.heading-tertiary "View Project Page"]
   [project-picker props]
   [project-display-form props]])
