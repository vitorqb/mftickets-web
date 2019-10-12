(ns mftickets-web.components.create-project-page
  (:require
   [mftickets-web.components.project-form :as components.project-form]
   [mftickets-web.components.create-project-page.queries :as queries]
   [mftickets-web.components.create-project-page.handlers :as handlers]
   [mftickets-web.components.message-box :as components.message-box]))

(def base-class "create-project-page")
(def loading-wrapper-class (str base-class "__loading-wrapper"))

(def ^:private project-form-selected-inputs-metadata
  "Metadata for inputs from project-form that should be displayed."
  [components.project-form/name-input-metadata
   components.project-form/description-input-metadata])

(defn- message-box
  [{:keys [state]}]
  (if-let [[style message] (queries/user-message @state)]
    [components.message-box/message-box {:style style :message message}]))

(defn- loading-wrapper
  "A wrapper for when we are loading..."
  [{:keys [state]}]
  (when (queries/loading? @state)
    [:div {:class [loading-wrapper-class]} "Loading..."]))

(defn- project-form
  "A wrapper around project-form"
  [{:keys [state] :as props}]
  (let [raw-project (queries/raw-project @state)
        events {:EditedProjectChange-> handlers/->RawProjectChange
                :Submit-> #(handlers/->CreateProjectSubmit props)}]
    [components.project-form/project-form
     {:project-form/inputs-metadata project-form-selected-inputs-metadata
      :project-form/edited-project raw-project
      :events events
      :parent-props props}]))

(defn create-project-page
  "A page component to create a new project."
  [{:keys [state] :as props}]
  [:div {:class [base-class]}
   [:h3.heading-tertiary "Create Project Page!"]
   [loading-wrapper props]
   [message-box props]
   [project-form props]])
