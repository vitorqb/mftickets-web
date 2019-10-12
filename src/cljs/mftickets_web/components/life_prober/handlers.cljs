(ns mftickets-web.components.life-prober.handlers
  (:require
   [mftickets-web.components.life-prober.queries :as queries]
   [mftickets-web.components.life-prober.reducers :as reducers]
   [cljs.core.async :as async]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defrecord Ping--after [response]
  events.protocols/PEvent
  (reduce! [_] (reducers/after-ping response)))

(defrecord Ping [props]
  events.protocols/PEvent
  (reduce! [_] (reducers/before-ping))
  (run-effects! [_]
    (let [ping (-> props :http :ping)]
      (async/go [(->> (ping) async/<! ->Ping--after)]))))

