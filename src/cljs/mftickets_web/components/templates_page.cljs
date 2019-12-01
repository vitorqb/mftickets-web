(ns mftickets-web.components.templates-page
  (:require
   [mftickets-web.components.templates-page.queries :as queries]
   [mftickets-web.components.templates-page.reducers :as reducers]
   [mftickets-web.components.templates-page.handlers :as handlers]
   [mftickets-web.events :as events]))

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

;; !!!! TODO -> We need to adapt this for pagination!
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

(defn templates-page
  "A page for displaying a list of templates."
  [{:keys [state] :as props}]
  (init! props)
  [:div.main {}
   [:h3.heading-tertiary "Templates Page"]
   [refresh-button props]
   [templates-table props]])
