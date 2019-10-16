(ns mftickets-web.instances.router-dialog
  (:require
   [mftickets-web.components.router-dialog :as components.router-dialog]
   [mftickets-web.components.dialog :as components.dialog]
   [mftickets-web.components.router-input :as components.router-input]
   [mftickets-web.state :as state]
   [mftickets-web.events :as events]
   [mftickets-web.app.handlers :as handlers]))

;; !!!! TODO -> Give real options
(def options
  [{:label "Templates" :href "/templates"}
   {:label "Templates - View [Templates]" :href "/templates/view"}
   {:label "Projects" :href "/projects"}
   {:label "Projects - Edit [Projects]" :href "/projects/edit"}
   {:label "Projects - Create [Projects]" :href "/projects/create"}
   {:label "Projects - View [Projects]" :href "/projects/view"}
   {:label "About" :href "/about"}
   {:label "Home" :href "/"}
   {:label "Configurations [Options]" :href "/config"}])

(defn router-dialog-instance
  [{:keys [app-state http]}]
  [components.router-dialog/router-dialog
   {:router-dialog/options options
    :state      (state/->FocusedAtom app-state [::state])
    :http       http
    :components {:dialog components.dialog/dialog
                 :router-input components.router-input/router-input}
    :events     {:Navigate-> handlers/->Navigate
                 :CloseRouterDialog-> handlers/->CloseRouterDialog}
    :parent-props {:state app-state}}])
