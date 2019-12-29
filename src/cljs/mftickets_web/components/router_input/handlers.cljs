(ns mftickets-web.components.router-input.handlers
  (:require
   [mftickets-web.components.router-input.reducers :as reducers]
   [mftickets-web.components.router-input.reducers :as queries]))

(defn on-input-change [{:keys [state]} new-value]
  (swap! state (reducers/set-input-value new-value)))

(defn- on-arrow-input-key-down
  [{:keys [state] :router-input/keys [matching-options]} key]
  (swap! state (reducers/select-from-key matching-options key)))

(defn- on-enter-input-key-down*
  [{:router-input.messages/keys [navigate close-router-dialog]
    :router-input/keys [selected-option]}
   key]
  {:pre [(ifn? navigate) (ifn? close-router-dialog)]}
  (when (= key "Enter")
    [[navigate (-> selected-option :href)]
     [close-router-dialog]]))

(defn- on-enter-input-key-down [props key]
  (doseq [[fn & args] (on-enter-input-key-down* props key)]
    (apply fn args)))

(defn on-input-key-down [props key]
  (on-arrow-input-key-down props key)
  (on-enter-input-key-down props key))
