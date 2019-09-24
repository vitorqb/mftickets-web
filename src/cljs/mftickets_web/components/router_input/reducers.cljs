(ns mftickets-web.components.router-input.reducers
  (:require
   [mftickets-web.domain.matcher :as domain.matcher]
   [mftickets-web.components.router-input.queries :as queries]
   [mftickets-web.domain.selector :as domain.selector]))

(defn set-input-value [x] #(assoc % :input-value x))
(defn append-to-selection-history [x] #(update % :selection-history conj x))

(defn- update-selection-history
  "Helper function that updates the selection history using a fn that returns
  the next history."
  [state update-history-fn]
  (->> state queries/selection-history update-history-fn (assoc state :selection-history)))

(defn select-next [matching-options]
  "Updates history to select next option."
  (fn [state]
    (update-selection-history state #(domain.selector/select-next matching-options %))))

(defn select-previous [matching-options]
  "Updates history to select previous option."
  (fn [state]
    (update-selection-history state #(domain.selector/select-previous matching-options %))))

(defn select-from-key [matching-options key]
  "Selects next, previous or current from a pressed key."
  (case key
    "ArrowUp"   (select-previous matching-options)
    "ArrowDown" (select-next matching-options)
    identity))
