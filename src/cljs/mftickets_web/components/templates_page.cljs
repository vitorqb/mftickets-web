(ns mftickets-web.components.templates-page
  (:require
   [mftickets-web.components.templates-page.queries :as queries]
   [mftickets-web.components.templates-page.reducers :as reducers]
   [mftickets-web.components.templates-page.handlers :as handlers]
   [mftickets-web.components.table-controllers.core :as components.table-controllers]
   [mftickets-web.state :as state]))

;; Css
(def ^:private base-class "templates-page")
(def ^:private loading-wrapper-class (str base-class "__loading-wrapper"))

;; Helpers
(defn- init!
  "Handles initialization logic."
  [{:keys [state] :as props}]
  (when-not (queries/templates-http-response @state)
    (handlers/init props)))

(def ^:private templates-table-config
  "A config for the templates table."
  [{:table/key :id            :table/header "Id"}
   {:table/key :name          :table/header "Name"}
   {:table/key :project-id    :table/header "Project Id"}
   {:table/key :creation-date :table/header "Creation Date"}])

;; Components
(defn- loading-wrapper [{:keys [state]}]
  (when (queries/is-loading? @state)
    [:div {:class loading-wrapper-class} "Loading..."]))

(defn- templates-table
  "A table with the templates information."
  [{{:keys [table]} :components :keys [state]}]
  (let [props* {:table/config templates-table-config
                :table/rows   (queries/templates @state)}]
    [table props*]))

(defn- refresh-button
  "A button to refresh the page."
  [props]
  [:div
   [:button {:on-click #(handlers/on-fetch-templates props)}
    "Refresh"]])

(defn- table-controller
  "User-displayable configuration for the table."
  [{:keys [state] :as props}]
  [components.table-controllers/table-controller
   {:table-controllers/current-page (queries/current-page @state)
    :table-controllers/page-count (queries/page-count @state)
    :table-controllers/current-page-size (queries/current-page-size @state)
    :table-controllers.messages/on-page-change #(handlers/on-page-change props %)
    :table-controllers.messages/on-page-size-change #(handlers/on-page-size-change props %)
    :state (state/->FocusedAtom state [:table-controller])}])

(defn templates-page
  "A page for displaying a list of templates."
  [{:keys [state] :as props}]
  (init! props)
  [:div.main {:class base-class}
   [loading-wrapper props]
   [:h3.heading-tertiary "Templates Page"]
   [table-controller props]
   [refresh-button props]
   [templates-table props]])
