(ns mftickets-web.components.life-prober.handlers
  (:require
   [mftickets-web.components.life-prober.queries :as queries]
   [mftickets-web.components.life-prober.reducers :as reducers]
   [cljs.core.async :as async]))

(defn ping
  "Handler to ping the server and set the status."
  [{:keys [state reduce! http]}]
  (let [ping! (:ping http)]
    (fn h-ping []
      (reduce! (reducers/before-ping))
      (async/go (-> (ping!) async/<! reducers/after-ping reduce!)))))

