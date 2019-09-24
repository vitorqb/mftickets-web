(ns mftickets-web.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.session :as session]
   [reitit.frontend :as reitit]
   [clerk.core :as clerk]
   [accountant.core :as accountant]
   [cljs.core.async :as async]
   [mftickets-web.instances.login-page :as instances.login-page]
   [mftickets-web.instances.header :as instances.header]
   [mftickets-web.instances.router-dialog :as instances.router-dialog]
   [mftickets-web.http :as http]))

;; -------------------------
;; Routes

(def router
  (reitit/router
   [["/" :index]
    ["/items"
     ["" :items]
     ["/:item-id" :item]]
    ["/about" :about]]))

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
    :ping http/ping}
   app-state))

(def injections
  {:app-state app-state :http http})

(defn home-page []
  (fn []
    [:div.main
     (if-let [token (:token @app-state)]
       [:div "You are logged in!"]
       [instances.login-page/login-page-instance injections])]))

(defn items-page []
  (fn []
    [:span.main
     [:h1 "The items of mftickets-web"]
     [:ul (map (fn [item-id]
                 [:li {:name (str "item-" item-id) :key (str "item-" item-id)}
                  [:a {:href (path-for :item {:item-id item-id})} "Item: " item-id]])
               (range 1 60))]]))

(defn item-page []
  (fn []
    (let [routing-data (session/get :route)
          item (get-in routing-data [:route-params :item-id])]
      [:span.main
       [:h1 (str "Item " item " of mftickets-web")]
       [:p [:a {:href (path-for :items)} "Back to the list of items"]]])))

(defn about-page []
  (fn [] [:span.main
          [:h1 "About mftickets-web"]]))

;; -------------------------
;; Routing
(defn page-for
  "Translate routes -> page components"
  [route]
  (case route
    :index #'home-page
    :about #'about-page
    :items #'items-page
    :item #'item-page))

;; -------------------------
;; Page mounting component
(defn current-page []
  (fn []
    (let [page (:current-page (session/get :route))]
      [:div
       [instances.router-dialog/router-dialog-instance injections]
       [:header [instances.header/header-instance injections]]
       [page]
       [:footer]])))

;; -------------------------
;; Initialize app
(defn maybe-try-to-set-token-from-cookies!
  "Tries to set token from cookies, if token is not already set."
  []
  (when-not (:token @app-state)
    (async/go
      (if-let [token (some-> ((http/get-token-from-cookies {})) async/<! :body :token :value)]
        (swap! app-state assoc :token token)))))

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
