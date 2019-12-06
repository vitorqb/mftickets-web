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
   [mftickets-web.instances.edit-template-page :as instances.edit-template-page]))

;; -------------------------
;; Routes

(def router
  (reitit/router
   [["/" :index]
    ["/about" :about]
    ["/templates" :templates]
    ["/templates/view" :view-templates]
    ["/templates/edit" :edit-templates]
    ["/projects" :projects]
    ["/projects/edit" :edit-projects]
    ["/projects/create" :create-projects]
    ["/projects/view" :view-projects]
    ["/config" :config-page]]))

(defn path-for [route & [params]]
  (if params
    (:path (reitit/match-by-name router route params))
    (:path (reitit/match-by-name router route))))

(path-for :about)
;; -------------------------
;; Page components
(defonce app-state
  (atom {}))

(def http
  (http/http-getter
   {:send-key http/send-key
    :get-token http/get-token
    :ping http/ping
    :get-templates http/get-templates
    :edit-template http/edit-template
    :get-matching-templates http/get-matching-templates
    :get-projects http/get-projects
    :get-app-metadata http/get-app-metadata
    :edit-project http/edit-project
    :create-project http/create-project
    :delete-project http/delete-project}
   app-state))

(def injections
  {:app-state app-state :http http})

(defn home-page [] [:div.main [:div "You are logged in!"]])

(defn about-page []
  (fn [] [:span.main
          [:h1 "About mftickets-web"]]))

(defn templates-page []
  [instances.templates-page/templates-page-instance injections])

(defn view-template-page []
  [instances.view-template-page/view-template-page-instance injections])

(defn edit-template-page []
  [instances.edit-template-page/edit-template-page-instance injections])

(defn projects-page []
  [instances.projects-page/projects-page-instance injections])

(defn edit-project-page []
  [instances.edit-project-page/edit-project-page-instance injections])

(defn create-project-page []
  [instances.create-project-page/create-project-page-instance injections])

(defn view-project-page []
  [instances.view-project-page/view-project-page-instance injections])

(defn config-page []
  [instances.config-page/config-page-instance injections])

;; -------------------------
;; Routing
(defn page-for
  "Translate routes -> page components"
  [route]
  (case route
    :index #'home-page
    :about #'about-page
    :templates #'templates-page
    :view-templates #'view-template-page
    :edit-templates #'edit-template-page
    :projects #'projects-page
    :edit-projects #'edit-project-page
    :create-projects #'create-project-page
    :view-projects #'view-project-page
    :config-page #'config-page))

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

(defn init! []
  (clerk/initialize!)
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (let [match (reitit/match-by-path router path)
            current-page (:name (:data  match))
            route-params (:path-params match)]
        (reagent/after-render clerk/after-render!)
        (session/put! :route {:current-page (page-for current-page)
                              :route-params route-params})
        (clerk/navigate-page! path)
        ))
    :path-exists?
    (fn [path]
      (boolean (reitit/match-by-path router path)))})
  (accountant/dispatch-current!)
  (mount-root))
