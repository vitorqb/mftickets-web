(ns mftickets-web.components.table-controllers.page-updown
  (:require [mftickets-web.components.button :as components.button]
            [mftickets-web.components.table-controllers.css :as css]))

;; Helpers
(defn- get-current-page-label
  [{:table-controllers/keys [current-page page-count]}]
  (str "Page " current-page " of " page-count))

;; Components
(defn- btn+
  "Button to increase the page count"
  [{:table-controllers/keys [current-page page-count]
    :table-controllers.messages/keys [on-page-change]}]

  {:pre [(int? current-page)]}

  [components.button/button
   {:button/label "+"
    :button.messages/on-click #(-> current-page inc on-page-change)}])

(defn- btn-
  "Button to decrease the page count"
  [{:table-controllers/keys [current-page page-count]
    :table-controllers.messages/keys [on-page-change]}]

  {:pre [(int? current-page)]}

  [components.button/button
   {:button/label "-"
    :button/style :button/danger
    :button.messages/on-click #(-> current-page dec on-page-change)}])

(defn page-updown
  "An input used by the user to control the page and allowing him to move up or down."
  [props]
  [:div {}
   [:span {:class css/label-class} (get-current-page-label props)]
   [:div {:class css/content-class}
    [btn- props]
    [btn+ props]]])
