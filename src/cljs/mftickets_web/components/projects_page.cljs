(ns mftickets-web.components.projects-page
  (:require
   [mftickets-web.components.projects-page.handlers :as handlers]
   [mftickets-web.components.projects-page.queries :as queries]
   [mftickets-web.components.table :as components.table]
   [mftickets-web.events :as events]))

(def base-class "projects-page")
(def loading-wrapper-class (str base-class "__loading-wrapper"))
(def loading-wrapper-inactive-modifier (str loading-wrapper-class "--inactive"))

(def ^:private projects-table-config
  "A config for the projects table."
  [#:table{:key :id :header "Id"}
   #:table{:key :name :header "Name"}
   #:table{:key :description :header "Description"}])

(defn- get-loading-wrapper-class
  "The class for the loading wrapper."
  [is-loading?]
  (cond-> [loading-wrapper-class]
    (not is-loading?) (conj loading-wrapper-inactive-modifier)))

(defn- loading-wrapper
  "A wrapper when the table is loading..."
  [{:keys [state]} & children]
  (let [loading-class (-> @state queries/loading? get-loading-wrapper-class)]
    [:<>
     [:div {:class loading-class} "Loading..."]
     children]))

(defn- refresh-button
  "A button to update the current list of projects."
  [props]
  [:button {:on-click #(events/react! props (handlers/->FetchProjects props))}
   "Refresh"])

(defn- projects-table
  "A wrapper around table for projects."
  [{:keys [state] :as props}]
  [components.table/table
   #:table{:config projects-table-config
           :rows (queries/projects @state)}])

(defn projects-page
  "A component for a page of projects."
  [{:keys [state] :as props}]

  (when (and (-> @state queries/fetch-projects-response nil?)
             (-> @state queries/loading? false?))
    (events/react! props (handlers/->Init props)))

  [:div.projects-page
   [:h3.heading-tertiary "PROJECTS PAGE"]
   [loading-wrapper props
    ^{:key "refresh-button"} [refresh-button props]
    ^{:key "projects-table"} [projects-table props]]])
