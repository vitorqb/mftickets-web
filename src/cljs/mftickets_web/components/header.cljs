(ns mftickets-web.components.header
  (:require
   [mftickets-web.components.life-prober :as components.life-prober]))

(def base-class "header")

(defn- life-prober
  "Wrapper for a life prober."
  [{:keys [state reduce!] :as props}]
  (let [-state   (::life-prober state)
        -reduce! (fn [reducer] (reduce! #(update % ::life-prober reducer)))]
    [components.life-prober/life-prober
     (assoc props :state -state :reduce! -reduce!)]))

(defn header
  "The header of the app."
  [props]
  [:header {:class [base-class]}
   [life-prober props]])
