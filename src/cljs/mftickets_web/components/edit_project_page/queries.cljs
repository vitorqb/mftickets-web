(ns mftickets-web.components.edit-project-page.queries
  (:require [mftickets-web.user-messages :as user-messages]
            [cljs.spec.alpha :as spec]))

(defn picked-project [x] (:picked-project x))

(defn edited-project
  "Returns the project being edited. If none is found, returns the picked project instead."
  [x]
  (or (:edited-project x)
      (picked-project x)))

(defn loading? [x] (:loading? x))

(defn edit-project-response
  "Get's the http response after submitting a project for edit."
  [x]
  (:edit-project-response x))

(defn user-message
  "A message informing the user about the current status."
  [x]
  {:post [(spec/valid? (spec/nilable :user-messages/style-and-message) %)]}

  (case (some-> x edit-project-response :success)
    nil   nil
    true  [:success user-messages/success]
    false [:error user-messages/generic-error]))
