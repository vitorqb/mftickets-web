(ns mftickets-web.components.view-project-page.handlers
  (:require [mftickets-web.components.view-project-page.reducers :as reducers]
            [cljs.spec.alpha :as spec]
            [mftickets-web.components.view-project-page.queries :as queries]
            [cljs.core.async :as async]))

;; Helpers
(defn- on-delete-picked-project-prompt
  "The prompt used to ask the user for deletion of a picked project."
  [{:keys [name]}]
  {:pre [(spec/valid? string? name)]
   :post [(spec/valid? string? %)]}
  (str "Are you sure you want to delete: " name "?"))

;; Events
(defn on-picked-project-change [{:keys [state]} picked-project]
  (swap! state (reducers/set-picked-project picked-project)))

(defn- after-perform-delete-picked-project
  [{:keys [state] :view-project-page.messages/keys [refresh-app-metadata]} response]
  (swap! state (reducers/after-delete-picked-project response))
  (refresh-app-metadata))

(defn- perform-delete-picked-project
  [{{:keys [delete-project]} :http :as props} project]
  (async/go
    (->> project delete-project async/<! (after-perform-delete-picked-project props))))

(defn on-delete-picked-project
  [{:keys [state] :view-project-page.messages/keys [with-confirmation] :as props}]
  (let [picked-project (queries/picked-project @state)
        perform-msg #(perform-delete-picked-project props picked-project)
        confirmation-prompt (on-delete-picked-project-prompt picked-project)
        with-confirmation-opts {:props props :message perform-msg :prompt confirmation-prompt}]
    (with-confirmation with-confirmation-opts)))
