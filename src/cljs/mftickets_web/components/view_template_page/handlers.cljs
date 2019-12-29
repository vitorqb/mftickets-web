(ns mftickets-web.components.view-template-page.handlers
  (:require [mftickets-web.components.view-template-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.view-template-page.queries :as queries]))

;; Helpers
(defn- delete-template-confirmation-prompt
  [{:keys [state]}]
  (let [{name :name} (queries/picked-template @state)]
    (str "Are you sure you want to delete '" name "'?")))

;; Handlers
(defn on-picked-template-change [{:keys [state]} template]
  (swap! state (reducers/on-picked-template template)))

(defn before-delete-template [{:keys [state]}]
  (swap! state (comp (reducers/set-delete-template-response nil)
                     (reducers/set-is-loading? true))))

(defn after-delete-template [{:keys [state]} response]
  (swap! state (cond-> (comp (reducers/set-delete-template-response response)
                             (reducers/set-is-loading? false))
                 (:success response) (comp (reducers/on-picked-template nil)))))

(defn perform-delete-template
  "Performs the deletion of the current template."
  [{{delete-template :delete-template} :http
    state :state
    :as props}]
  (before-delete-template props)
  (async/go
    (->> @state queries/picked-template delete-template async/<! (after-delete-template props))))

(defn on-delete-template
  "Prompts the user whether to delete a template and dispatches on confirmation."
  [{:keys [state] :view-template-page.messages/keys [with-confirmation] :as props}]
  (let [opts {:message #(perform-delete-template props)
              :prompt (delete-template-confirmation-prompt props)}]
    (with-confirmation opts)))
