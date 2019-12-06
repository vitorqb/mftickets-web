(ns mftickets-web.components.edit-template-page.handlers
  (:require [mftickets-web.components.edit-template-page.reducers :as reducers]
            [cljs.core.async :as async]
            [mftickets-web.components.edit-template-page.queries :as queries]))

(defn on-template-picked [{:keys [state]} template]
  (swap! state (comp (reducers/set-edited-template template)
                     (reducers/set-picked-template template))))

(defn on-edited-template-change [{:keys [state]} new-template]
  (swap! state (reducers/set-edited-template new-template)))

(defn- before-edited-template-submit [{:keys [state]}]
  (swap! state (reducers/before-edited-template-submit)))

(defn- after-edited-template-submit
  [{:keys [state]} response]
  (swap! state (reducers/after-edited-template-submit response)))

(defn on-edited-template-submit
  [{state :state {:keys [edit-template]} :http :as props}]

  {:pre [(fn? edit-template) (or (satisfies? IAtom state) (satisfies? ISwap state))]}
  
  (before-edited-template-submit props)
  (async/go
    (->> @state
         queries/edited-template
         edit-template
         async/<!
         (after-edited-template-submit props))))
