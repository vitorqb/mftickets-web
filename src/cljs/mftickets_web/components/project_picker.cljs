(ns mftickets-web.components.project-picker
  (:require
   [mftickets-web.components.select :as components.select]
   [cljs.spec.alpha :as s]
   [mftickets-web.components.project-picker.handlers :as handlers]))

;; Css
(def base-class "project-picker")

;; Specs
(s/def :project-picker/projects (s/nilable (s/coll-of any?)))

(s/def :project-picker.events/Change-> fn?)
(s/def :project-picker/events (s/keys :req-un [:project-picker.events/Change->]))

(s/def :project-picker/props (s/keys :req [:project-picker/projects]
                                     :req-un [:project-picker/events]))

;; Helpers
(defn- project->select-option
  "Maps a project to a Select option."
  [{:keys [name] :as project}]
  (when project
    {:value project :label name}))

;; Components
(defn project-picker
  "A component for the user to pick a project from a list."
  [{:keys [events]
    :project-picker/keys [projects picked-project]
    :as props}]
  {:pre [(s/assert :project-picker/props props)]}
  
  (let [value (project->select-option picked-project)
        options (map project->select-option projects)
        props* (merge
                {:events {:Change-> #(handlers/->Change props %)}
                 :parent-props props}
                #:select{:value value :options options})]
    [:div {:class [base-class]}
     [components.select/select props*]]))
