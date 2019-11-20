(ns mftickets-web.components.config-page
  (:require [mftickets-web.components.project-picker :as components.project-picker]
            [cljs.spec.alpha :as spec]))

;; Css
(def base-class "config-page")
(def active-project-picker-wrapper-class (str base-class "__active-project-picker"))

;; Specs
(spec/def :config-page.messages/update-current-project ifn?)

(spec/def :config-page/projects (spec/nilable (spec/coll-of any?)))
(spec/def :config-page/active-project any?)
(spec/def :config-page/props
  (spec/keys
   :req [:config-page/projects
         :config-page/active-project
         :config-page.messages/update-current-project]))

;; Components
(defn- active-project-picker
  "An picker for the current project."
  [{:config-page/keys [projects active-project]
    :config-page.messages/keys [update-current-project] 
    :as props}]

  {:pre [(ifn? update-current-project)]}

  [components.project-picker/project-picker
   {:project-picker/projects projects
    :project-picker/picked-project active-project
    :project-picker.messages/on-picked-project-change #(update-current-project %)}])

(defn config-page
  "A page for user configuration."
  [props]
  {:pre [(spec/assert :config-page/props props)]}

  [:div
   [:h3.heading-tertiary "CONFIG PAGE"]
   [:span.featured-label-1 "Active Project:"]
   [:div {:class [active-project-picker-wrapper-class]}
    [active-project-picker props]]])
