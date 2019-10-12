(ns mftickets-web.components.project-picker
  (:require
   [mftickets-web.components.select :as components.select]
   [cljs.spec.alpha :as s]
   [mftickets-web.components.project-picker.handlers :as handlers]))

;; Css
(def base-class "project-picker")

;; Specs
(s/def ::projects (s/coll-of any?))

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
  {:pre [(s/valid? ::projects projects)]}
  
  (let [value (project->select-option picked-project)
        options (map project->select-option projects)
        props* (merge
                {:events {:on-change-> #(handlers/->Change props %)}
                 :parent-props props}
                #:select{:value value :options options})]
    [:div {:class [base-class]}
     [components.select/select props*]]))
