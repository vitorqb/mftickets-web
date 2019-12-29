(ns mftickets-web.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.session :as session]
   [reitit.frontend :as reitit]
   [clerk.core :as clerk]
   [accountant.core :as accountant]
   [cljs.core.async :as async]
   [mftickets-web.instances.current-page :as instances.current-page]
   [mftickets-web.instances.templates-page :as instances.templates-page]
   [mftickets-web.instances.projects-page :as instances.projects-page]
   [mftickets-web.instances.edit-project-page :as instances.edit-project-page]
   [mftickets-web.instances.create-project-page :as instances.create-project-page]
   [mftickets-web.instances.view-project-page :as instances.view-project-page]
   [mftickets-web.instances.config-page :as instances.config-page]
   [mftickets-web.http :as http]
   [mftickets-web.app.handlers :as app.handlers]
   [mftickets-web.app.queries :as app.queries]
   [mftickets-web.instances.view-template-page :as instances.view-template-page]
   [mftickets-web.instances.edit-template-page :as instances.edit-template-page]
   [mftickets-web.instances.create-template-page :as instances.create-template-page]
   [mftickets-web.app.reducers :as reducers]))

;; -------------------------
;; Page config
(defonce app-state
  (atom {}))

(def http
  (http/http-getter
   {:send-key http/send-key
    :get-token http/get-token
    :ping http/ping
    :get-templates http/get-templates
    :edit-template http/edit-template
    :create-template http/create-template
    :delete-template http/delete-template
    :get-matching-templates http/get-matching-templates
    :get-projects http/get-projects
    :get-app-metadata http/get-app-metadata
    :edit-project http/edit-project
    :create-project http/create-project
    :delete-project http/delete-project}
   app-state))

(def router
  (reitit.frontend/router
   [["/"
     {:name :index
      :label "Index"
      :component (fn [_] [:div.main [:div "You are logged in!"]])}]

    ["/about"
     {:name :about
      :label "About"
      :component (fn [_] [:span.main [:h1 "About mftickets-web"]])}]

    ["/templates"
     {:name :templates
      :label "List Templates [List]"
      :component instances.templates-page/templates-page-instance}]

    ["/templates/view"
     {:name :view-templates
      :label "View Templates [View]"
      :component instances.view-template-page/view-template-page-instance}]

    ["/templates/edit"
     {:name :edit-templates
      :label "Edit Templates [Edit]"
      :component instances.edit-template-page/edit-template-page-instance}]

    ["/templates/create"
     {:name :create-templates
      :label "Create Templates [Create]"
      :component instances.create-template-page/create-template-page-instance}]

    ["/projects"
     {:name :projects
      :label "List Projects [List]"
      :component instances.projects-page/projects-page-instance}]

    ["/projects/edit"
     {:name :edit-project
      :label "Edit Projects [Edit]"
      :component instances.edit-project-page/edit-project-page-instance}]

    ["/projects/create"
     {:name :create-project
      :label "Create Projects [Create]"
      :component instances.create-project-page/create-project-page-instance}]

    ["/projects/view"
     {:name :view-project
      :label "View Projects [View]"
      :component instances.view-project-page/view-project-page-instance}]

    ["/config"
     {:name :config-page
      :label "Configurations"
      :component instances.config-page/config-page-instance}]]))

(def injections {:app-state app-state :http http :router router})

;; -------------------------
;; Page mounting component
(defn current-page [] [instances.current-page/current-page-instance injections])

;; -------------------------
;; Initialize app
(defn- maybe-try-to-set-token-from-cookies!
  "Tries to set token from cookies, if token is not already set."
  []
  (when-not (:token @app-state)
    (async/go
      (when-let [token (some-> ((http/get-token-from-cookies {})) async/<! :body :token :value)]
        (app.handlers/update-token injections token)))))

(defn mount-root []
  (maybe-try-to-set-token-from-cookies!)
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (clerk/initialize!)
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (let [match (reitit/match-by-path router path)]
        (reagent/after-render clerk/after-render!)
        (swap! app-state (reducers/set-current-routing-match match))
        (clerk/navigate-page! path)))
    :path-exists?
    (fn [path]
      (boolean (reitit/match-by-path router path)))})
  (accountant/dispatch-current!)
  (mount-root))
