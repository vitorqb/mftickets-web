(ns mftickets-web.components.edit-project-page
  (:require
   [mftickets-web.components.project-form :as components.project-form]
   [mftickets-web.components.project-picker :as components.project-picker]
   [mftickets-web.state :as state]
   [cljs.spec.alpha :as s]
   [mftickets-web.components.edit-project-page.queries :as queries]
   [mftickets-web.components.edit-project-page.handlers :as handlers]
   [mftickets-web.components.message-box :as components.message-box]
   [mftickets-web.components.factories.loading-wrapper :as c.factories.loading-wrapper :refer-macros [def-loading-wrapper]]))

(def base-class "edit-project-page")
(def project-picker-wrapper-class (str base-class "__project-picker-wrapper"))
(def project-picker-contents-class (str base-class "__project-picker-contents"))
(def loading-wrapper-class (str base-class "__loading-wrapper"))

(defn- project-form
  "A wrapper around project-form component."
  [{:keys [state]
    :as props}]
  (if-let [picked-project (queries/picked-project @state)]
    (let [edited-project (queries/edited-project @state)
          props* {:project-form/original-project picked-project
                  :project-form/edited-project edited-project
                  :project-form.messages/on-edited-project-change
                  #(handlers/on-project-form-edited-project-change props %)
                  :project-form.messages/on-edited-project-submit
                  #(handlers/on-edited-project-submit props)
                  :state (state/->FocusedAtom state [::project-form])}]
      [components.project-form/project-form props*])))

(defn- project-picker
  "A wrapper around project-picker component."
  [{:edit-project-page/keys [projects]
    :keys [state]
    :as props}]

  (let [picked-project (queries/picked-project @state)
        projects* (or projects [])
        props* {:project-picker/projects projects*
                :project-picker/picked-project picked-project
                :project-picker.messages/on-picked-project-change
                #(handlers/on-picked-project-change props %)}]
  
    [:div {:class [project-picker-wrapper-class]}
     [:span.featured-label-1 "Pick a project:"]
     [:div {:class [project-picker-contents-class]}
      [components.project-picker/project-picker props*]]]))

(def-loading-wrapper loading-wrapper queries/loading? loading-wrapper-class)

(defn- message-box
  "A wrapper around message-box displaying a message for the user."
  [{:keys [state]}]
  (if-let [[style message] (queries/user-message @state)]
    [components.message-box/message-box {:message message :style style}]))

(defn edit-project-page
  "A page to edit a project."
  [{:edit-project-page/keys [projects]
    :keys [state]
    :as props}]

  [:div {:class [base-class]}
   [:h3.heading-tertiary
    "Edit Project"]
   [loading-wrapper props]
   [project-picker props]
   [message-box props]
   [project-form props]])
