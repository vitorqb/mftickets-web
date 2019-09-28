(ns mftickets-web.components.router-input.handlers
  (:require
   [mftickets-web.components.router-input.reducers :as reducers]
   [mftickets-web.components.router-input.reducers :as queries]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn on-input-change
  "An event when the user input changes."
  [{:keys [state]} new-value]
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-input-value new-value))))

(defn on-input-key-up--arrows
  [{:router-input/keys [matching-options]} key]
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/select-from-key matching-options key))))

(defn on-input-key-up--enter
  [{{:keys [navigate-> close-router-dialog->]} :events
    :router-input/keys [selected-option]
    :as props}
   key]
  
  (reify events.protocols/PEvent
    (propagate! [_]
      (when (= key "Enter")
        [(-> selected-option :href navigate->)
         (close-router-dialog->)]))))

(defn on-input-key-up
  "An event representing a key press while the user was focusing the input."
  [props key]
  (reify events.protocols/PEvent
    (dispatch! [_] [(on-input-key-up--arrows props key)
                    (on-input-key-up--enter props key)])))
