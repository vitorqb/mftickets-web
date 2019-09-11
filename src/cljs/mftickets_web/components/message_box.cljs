(ns mftickets-web.components.message-box)

(def message-box-base-class "message-box")
(def message-box-message-class (str message-box-base-class "__mesage"))

(defn message-box
  "A components used to display a message for the user."
  [{:keys [message]}]
  [:div {:class [message-box-base-class]}
   [:span {:class [message-box-message-class]}
    message]])

