(ns mftickets-web.components.router-dialog)

(def body-base-class "router-dialog")

(defn- router-input
  "A wrapper around a router input component."
  [{:router-dialog/keys [options] :keys [components state reduce!] :as props}]
  (let [router-input (:router-input components)
        -state (::router-input state)
        -reduce! (fn [reducer] (reduce! #(update % ::router-input reducer)))]
    [router-input (assoc props :router-input/options options :state -state :reduce! -reduce!)]))

(defn- body
  "The body component for the router dialog."
  [props]
  [:div {:class [body-base-class]}
   [router-input props]])

(defn router-dialog
  "Provides a dialog for user routing."
  [{:keys [components state reduce!] :as props}]
  (let [dialog   (:dialog components)
        -state   (::dialog state)
        -reduce! (fn [reducer] (reduce! #(update % ::dialog reducer)))]
    [dialog (assoc props :dialog/body-component [body props] :state -state :reduce! -reduce!)]))
