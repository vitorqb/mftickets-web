(ns mftickets-web.components.create-template-page.handlers
  (:require [mftickets-web.components.create-template-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.create-template-page.queries :as queries]))

(defn- on-new-template-change [{:keys [state]} new-template]
  (swap! state (reducers/set-edited-template new-template)))

(defn- before-new-template-submit [{:keys [state]}]
  (swap! state (comp (reducers/set-is-loading? true)
                     (reducers/set-create-template-response nil))))

(defn after-new-template-submit [{:keys [state]} response]
  (swap! state (comp (reducers/set-is-loading? false)
                     (reducers/set-create-template-response response))))

(defn on-new-template-submit
  [{{create-template-fn :create-template} :http
    state :state
    :as props}]
  {:pre [(ifn? create-template-fn)]}
  (before-new-template-submit props)
  (async/go
    (-> @state
        (queries/edited-template props)
        create-template-fn
        async/<!
        (->> (after-new-template-submit props)))))
