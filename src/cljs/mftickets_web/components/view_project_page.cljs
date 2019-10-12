(ns mftickets-web.components.view-project-page
  (:require [mftickets-web.components.project-picker :as components.project-picker]
            [mftickets-web.components.view-project-page.queries :as queries]
            [mftickets-web.components.view-project-page.handlers :as handlers]
            [mftickets-web.events :as events]
            [mftickets-web.components.project-form :as components.project-form]
            [com.rpl.specter :as s]))

(def ^:private base-class "view-project-page")
(def ^:private project-picker-wrapper-class (str base-class "__project-picker-wrapper"))
(def ^:private project-picker-contents-class (str base-class "__project-picker-contents"))

(defn project-picker
  "Wrapper around project-picker. Allows the user to pick a project."
  [{:view-project-page/keys [projects]
    :keys [state]
    :as props}]

  (let [events {:Change-> #(->> % handlers/->PickedProjectChange (events/react! props))}
        picked-project (queries/picked-project @state)
        props {:project-picker/projects (or projects [])
               :project-picker/picked-project picked-project
               :events events}]

    [:div {:class [project-picker-wrapper-class]}
     [:span.featured-label-1 "Pick a project:"]
     [:div {:class [project-picker-contents-class]}
      [components.project-picker/project-picker props]]]))

(defn- project-display-form-inputs-metadata
  "Returns the metadata for the inputs of the project form."
  []
  (s/setval
   [s/ALL :disabled]
   true
   [components.project-form/id-input-metadata
    components.project-form/name-input-metadata
    components.project-form/description-input-metadata]))

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
