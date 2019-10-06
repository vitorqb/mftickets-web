(ns mftickets-web.components.create-project-page.queries
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.user-messages :as user-messages]))

(defn raw-project
  "Queries the user-inputted project."
  [x]
  (:raw-project x))

(defn loading? [x] (:loading? x))
(defn create-project-response [x] (:create-project-response x))

(defn user-message
  "The message to be displayed to the user."
  [x]
  {:post [(spec/valid? (spec/nilable :user-messages/style-and-message) %)]}
  (case (some-> x create-project-response :success)
    nil nil
    true [:success user-messages/success]
    false [:error user-messages/generic-error]))
