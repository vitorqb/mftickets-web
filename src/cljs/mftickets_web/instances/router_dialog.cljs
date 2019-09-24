(ns mftickets-web.instances.router-dialog
  (:require
   [mftickets-web.components.router-dialog :as components.router-dialog]
   [mftickets-web.components.dialog :as components.dialog]
   [mftickets-web.components.router-input :as components.router-input]
   [mftickets-web.state :as state]))

;; !!!! TODO -> Give real options
(def options
  [{:label "Template List [Template]" :href "#"}
   {:label "My Projects" :href "#"}
   {:label "About" :href "/about"}
   {:label "All you need" :href "#"}
   {:label "Is love!" :ref "#"}
   {:label "Some crazy router here" :ref "#"}])

(defn router-dialog-instance
  [{:keys [app-state http]}]
  [components.router-dialog/router-dialog
   {:router-dialog/options options
    :state      (state/->FocusedAtom app-state [::state])
    :http       http
    :components {:dialog components.dialog/dialog
                 :router-input components.router-input/router-input}}])
