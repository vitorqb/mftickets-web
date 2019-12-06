(ns mftickets-web.components.edit-template-page.queries
  (:require [mftickets-web.user-messages :as user-messages]
            [cljs.spec.alpha :as spec]))

(defn picked-template [x] (:picked-template x))
(defn edited-template [x] (:edited-template x))
(defn loading? [x] (:loading? x))
(defn edited-template-submit-response [x] (:edited-template-submit-response x))

(defn user-message [state]
  
  (when-let [response (edited-template-submit-response state)]
    (if (:success response)
      {:message user-messages/success
       :style :success}
      {:message user-messages/generic-error
       :style :error})))
