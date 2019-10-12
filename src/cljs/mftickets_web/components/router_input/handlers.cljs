(ns mftickets-web.components.router-input.handlers
  (:require
   [mftickets-web.components.router-input.reducers :as reducers]
   [mftickets-web.components.router-input.reducers :as queries]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defrecord InputChange [new-value]
  events.protocols/PEvent
  (reduce! [_] (reducers/set-input-value new-value)))

(defrecord InputKeyUp--arrows [props key]
  events.protocols/PEvent
  (reduce! [_]
    (let [matching-options (:router-input/matching-options props)]
      (reducers/select-from-key matching-options key))))

(defrecord InputKeyUp--enter [props key]
  events.protocols/PEvent
  (propagate! [_]
    (when (= key "Enter")
      (let [{:keys [Navigate-> CloseRouterDialog->]} (-> props :events)
            selected-option (:router-input/selected-option props)]
        [(-> selected-option :href Navigate->)
         (CloseRouterDialog->)]))))

(defrecord InputKeyUp [props key]
  events.protocols/PEvent
  (dispatch! [_] [(->InputKeyUp--arrows props key)
                  (->InputKeyUp--enter props key)]))
