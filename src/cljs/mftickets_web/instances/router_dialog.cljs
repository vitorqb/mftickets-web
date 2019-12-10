(ns mftickets-web.instances.router-dialog
  (:require
   [mftickets-web.components.router-dialog :as components.router-dialog]
   [mftickets-web.components.dialog :as components.dialog]
   [mftickets-web.components.router-input :as components.router-input]
   [mftickets-web.state :as state]
   [mftickets-web.app.handlers :as handlers]
   [mftickets-web.routing :as routing]))

(defn router-dialog-instance
  [{:keys [app-state http router] :as inject}]
  [components.router-dialog/router-dialog
   {:router-dialog/options (routing/router->user-routing-opts router)
    :router-dialog.messages/close-router-dialog #(handlers/close-router-dialog inject)
    :router-dialog.messages/navigate #(handlers/navigate inject %)
    :state      (state/->FocusedAtom app-state [::state])
    :http       http
    :components {:dialog components.dialog/dialog
                 :router-input components.router-input/router-input}
    :parent-props {:state app-state}}])
