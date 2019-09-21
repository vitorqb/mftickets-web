(ns mftickets-web.components.header
  (:require
   [mftickets-web.components.life-prober :as components.life-prober]
   [mftickets-web.components.header.handlers :as handlers]))

(def base-class "header")

(defn- life-prober
  "Wrapper for a life prober."
  [{:keys [state reduce!] :as props}]
  (let [-state   (::life-prober state)
        -reduce! (fn [reducer] (reduce! #(update % ::life-prober reducer)))]
    [components.life-prober/life-prober
     (assoc props :state -state :reduce! -reduce!)]))

(defn- router-btn
  "A button used to display the router dialog."
  [props]
  [:button {:on-click (handlers/display-router props)} "Router"])

(defn header
  "The header of the app."
  [props]
  [:header {:class [base-class]}
   [life-prober props]
   [router-btn props]])
