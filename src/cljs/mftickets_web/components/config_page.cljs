(ns mftickets-web.components.config-page
  (:require [mftickets-web.components.project-picker :as components.project-picker]
            [cljs.spec.alpha :as spec]
            [mftickets-web.components.config-page.handlers :as handlers]))

;; Css
(def base-class "config-page")
(def active-project-picker-wrapper-class (str base-class "__active-project-picker"))

;; Specs
(spec/def :config-page.events/UpdateCurrentProject-> fn?)
(spec/def :config-page/events (spec/keys :req-un [:config-page.events/UpdateCurrentProject->]))

(spec/def :config-page/projects (spec/nilable (spec/coll-of any?)))
(spec/def :config-page/active-project any?)
(spec/def :config-page/props (spec/keys :req [:config-page/projects :config-page/active-project]
                                        :req-un [:config-page/events]))

;; Components
(defn- active-project-picker
  "An picker for the current project."
  [{:config-page/keys [projects active-project] :as props}]
  [components.project-picker/project-picker
   {:project-picker/projects projects
    :project-picker/picked-project active-project
    :events {:Change-> #(handlers/->UpdateCurrentProject props %)}
    :parent-props props}])

(defn config-page
  "A page for user configuration."
  [props]
  {:pre [(spec/assert :config-page/props props)]}

  [:div
   [:h3.heading-tertiary "CONFIG PAGE"]
   [:span.featured-label-1 "Active Project:"]
   [:div {:class [active-project-picker-wrapper-class]}
    [active-project-picker props]]])
