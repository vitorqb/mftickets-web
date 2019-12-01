(ns mftickets-web.components.projects-page
  (:require
   [mftickets-web.components.projects-page.handlers :as handlers]
   [mftickets-web.components.projects-page.queries :as queries]
   [mftickets-web.components.table :as components.table]
   [cljs.spec.alpha :as spec]))

;; Css
(def base-class "projects-page")

;; Specs
(spec/def :projects-page/projects (spec/nilable vector?))
(spec/def :projects-page/props (spec/keys :req [:projects-page/projects]))

;; Helpers
(def ^:private projects-table-config
  "A config for the projects table."
  [#:table{:key :id :header "Id"}
   #:table{:key :name :header "Name"}
   #:table{:key :description :header "Description"}])

(defn- projects-table
  "A wrapper around table for projects."
  [{:projects-page/keys [projects]}]
  [components.table/table #:table{:config projects-table-config :rows projects}])

(defn projects-page
  "A component for a page of projects."
  [{:keys [state] :as props}]
  {:pre [(spec/valid? :projects-page/props props)]}
  [:div.projects-page
   [:h3.heading-tertiary "PROJECTS PAGE"]
   [projects-table props]])
