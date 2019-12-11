(ns mftickets-web.components.create-template-page.queries
  (:require [mftickets-web.domain.template :as domain.template]
            [mftickets-web.user-messages :as user-messages]))

;; Helpers
(defn- or-empty-template
  "If the given arg is nil, returns a new empty template, else returns the argument."
  [arg {{current-project-id :id} :create-template-page/current-project}]
  (or arg (domain.template/gen-empty-template {:project-id current-project-id})))

;; Queries
(defn edited-template
  "Returns the edited template given the state."
  [state {{project-id :id} :create-template-page/current-project :as props}]
  (-> state :edited-template (or-empty-template props) (assoc :project-id project-id)))

(defn is-loading? [x] (:is-loading? x))
(defn create-template-response [x] (:create-template-response x))

(defn user-message
  "Returns a user friendly message, usually with information after the request was
  submitted."
  [x]
  (if-let [response (create-template-response x)]
    (if (:success response)
      [:success user-messages/success]
      [:error user-messages/generic-error])))
