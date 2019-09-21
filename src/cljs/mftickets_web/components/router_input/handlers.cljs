(ns mftickets-web.components.router-input.handlers
  (:require
   [mftickets-web.components.router-input.reducers :as reducers]
   [mftickets-web.components.router-input.reducers :as queries]))

(defn on-input-key-up
  "A handler for a key press when the input is selected."
  [{:keys [reduce!] :router-input/keys [matching-options]}]
  (fn [key]
    (case key
      "ArrowDown" (reduce! (reducers/select-next matching-options))
      "ArrowUp"   (reduce! (reducers/select-previous matching-options))
      nil)))
