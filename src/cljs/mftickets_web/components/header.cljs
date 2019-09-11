(ns mftickets-web.components.header)

(def base-class "header")

(defn header
  "The header of the app."
  [_]
  [:header {:class [base-class]}
   [:span "Header!"]])
