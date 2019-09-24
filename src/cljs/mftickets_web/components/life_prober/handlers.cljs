(ns mftickets-web.components.life-prober.handlers
  (:require
   [mftickets-web.components.life-prober.queries :as queries]
   [mftickets-web.components.life-prober.reducers :as reducers]
   [cljs.core.async :as async]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(defn- after-ping
  "Events that represents that the ping ended."
  [_ response]

  (reify events.protocols/PEvent

    (reduce! [_] (reducers/after-ping response))))

(defn ping
  "Event to ping the server and set the response status."
  [{{:keys [ping]} :http :as props}]

  (reify events.protocols/PEvent

    (reduce! [_] (reducers/before-ping))

    (run-effects! [_] (async/go [(->> (ping) async/<! (after-ping props))]))))

