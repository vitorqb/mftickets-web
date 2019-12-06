(ns mftickets-web.components.message-box
  (:require [cljs.spec.alpha :as s]))

;; Css
(def message-box-base-class "message-box")
(def message-box-message-class (str message-box-base-class "__mesage"))
(def message-box-success-modifier (str message-box-base-class "--success"))
(def message-box-error-modifier (str message-box-base-class "--error"))

;; Helper
(defn- style->modifier
  "Given a style, returns the css modifier."
  [style]
  {:pre [(s/valid? :user-messages/style style)]}
  (case style
    :success message-box-success-modifier
    :error   message-box-error-modifier))

;; Component
(defn message-box
  "A components used to display a message for the user."
  [{:keys [message style] :or {style :error}}]
  {:pre [(s/assert :user-messages/message message)]}

  [:div {:class [message-box-base-class (style->modifier style)]}
   [:span {:class [message-box-message-class]}
    message]])

