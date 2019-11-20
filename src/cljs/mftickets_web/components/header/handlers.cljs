(ns mftickets-web.components.header.handlers
  (:require
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defrecord RefreshAppMetadata [props]
  events.protocols/PEvent
  (propagate! [_]
    (let [RefreshAppMetadata-> (-> props :events :RefreshAppMetadata->)
          _ (assert (fn? RefreshAppMetadata->))]
      [(RefreshAppMetadata->)])))
