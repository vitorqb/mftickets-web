(ns mftickets-web.components.header
  (:require
   [mftickets-web.components.life-prober :as components.life-prober]))

(def base-class "header")

(defn header
  "The header of the app."
  [_]
  [:header {:class [base-class]}
   [components.life-prober/life-prober {}]])
