(ns mftickets-web.instances.current-page
  (:require
   [reagent.session :as session]
   [mftickets-web.instances.login-page :as instances.login-page]
   [mftickets-web.instances.router-dialog :as instances.router-dialog]
   [mftickets-web.instances.header :as instances.header]
   [mftickets-web.components.current-page :as components.current-page]))

(defn current-page-instance
  "Instance holding the current-page component."
  [{:keys [app-state] :as injections}]

  (let [logged-in? (-> @app-state :token nil? not)
        login-page [instances.login-page/login-page-instance injections]
        router-dialog [instances.router-dialog/router-dialog-instance injections]
        header [instances.header/header-instance injections]
        page (:current-page (session/get :route))]

    [components.current-page/current-page
     #:current-page{:logged-in? logged-in?
                    :instances {:login-page login-page
                                :router-dialog router-dialog
                                :header header
                                :page page}}]))
