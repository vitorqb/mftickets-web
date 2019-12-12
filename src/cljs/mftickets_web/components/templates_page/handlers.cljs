(ns mftickets-web.components.templates-page.handlers
  (:require
   [mftickets-web.components.templates-page.reducers :as reducers]
   [cljs.core.async :as async]
   [mftickets-web.components.templates-page.queries :as queries]))

;; Helpers
(defn- page-in-acceptable-range
  [{:keys [state]} new-page]
  (and (>= new-page 1) (<= new-page (queries/page-count @state))))

;; Handlers
(defn after-fetch-templates [{:keys [state]} response]
  (swap! state (comp (reducers/set-templates-http-response response)
                     (reducers/set-is-loading? false))))

(defn on-fetch-templates
  [{{:keys [get-templates]} :http
    :templates-page/keys [current-project-id]
    :keys [state]
    :as props}]
  (async/go (->> {:project-id current-project-id
                  :pagination/page-number (queries/current-page @state)
                  :pagination/page-size (queries/current-page-size @state)}
                 get-templates
                 async/<!
                 (after-fetch-templates props))))

(defn init [props] (on-fetch-templates props))

(defn before-page-change [{:keys [state]} new-page]
  (swap! state (comp (reducers/set-is-loading? true)
                     (reducers/set-current-page new-page))))

(defn on-page-change [props new-page]
  (if-not (page-in-acceptable-range props new-page)
    ::page-outside-acceptable-range
    (do
      (before-page-change props new-page)
      (on-fetch-templates props))))

(defn on-page-size-change [{:keys [state] :as props} new-page-size]
  (swap! state (reducers/set-current-page-size new-page-size))
  (on-page-change props 1))
