(ns mftickets-web.components.router-input.handlers
  (:require
   [mftickets-web.components.router-input.reducers :as reducers]
   [mftickets-web.components.router-input.reducers :as queries]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn on-input-change [{:keys [state]} new-value]
  (swap! state (reducers/set-input-value new-value)))

(defn- on-arrow-input-key-up
  [{:keys [state] :router-input/keys [matching-options]} key]
  (swap! state (reducers/select-from-key matching-options key)))

(defn- on-enter-input-key-up*
  [{:router-input.messages/keys [navigate close-router-dialog]
    :router-input/keys [selected-option]}
   key]
  {:pre [(ifn? navigate) (ifn? close-router-dialog)]}
  (when (= key "Enter")
    [[navigate (-> selected-option :href)]
     [close-router-dialog]]))

(defn- on-enter-input-key-up [props key]
  (doseq [[fn & args] (on-enter-input-key-up* props key)]
    (apply fn args)))

(defn on-input-key-up [props key]
  (on-arrow-input-key-up props key)
  (on-enter-input-key-up props key))
