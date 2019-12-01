(ns mftickets-web.components.life-prober.handlers
  (:require
   [mftickets-web.components.life-prober.queries :as queries]
   [mftickets-web.components.life-prober.reducers :as reducers]
   [cljs.core.async :as async]))

(defn after-ping [{:keys [state]} response]
  (swap! state (reducers/after-ping response)))

(defn before-ping [{:keys [state]}]
  (swap! state (reducers/before-ping)))

(defn on-ping [{{:keys [ping]} :http :as props}]
  (before-ping props)
  (async/go (->> (ping) async/<! (after-ping props))))

