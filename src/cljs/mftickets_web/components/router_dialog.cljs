(ns mftickets-web.components.router-dialog
  (:require
   [mftickets-web.state :as state]
   [mftickets-web.events :as events]
   [mftickets-web.components.router-dialog.handlers :as handlers]))

(def body-base-class "router-dialog")

(defn- router-input
  "A wrapper around a router input component."
  [{:router-dialog/keys [options] :keys [components state] :as props}]
  (let [router-input (:router-input components)
        -state       (state/->FocusedAtom state [::router-input])
        -events      {:close-router-dialog-> #(handlers/close-router-dialog-> props)
                      :navigate-> #(handlers/navigate-> props %)}
        -parent-react! #(events/react! props %)
        -props       (assoc props
                            :router-input/options options
                            :state -state
                            :events -events
                            :parent-react! -parent-react!)]
    [router-input -props]))

(defn- body
  "The body component for the router dialog."
  [props]
  [:div {:class [body-base-class]}
   [router-input props]])

(defn router-dialog
  "Provides a dialog for user routing."
  [{:keys [components state] :as props}]
  (let [dialog   (:dialog components)
        -state   (state/->FocusedAtom state [::dialog])]
    [dialog (assoc props :dialog/body-component [body props] :state -state)]))
