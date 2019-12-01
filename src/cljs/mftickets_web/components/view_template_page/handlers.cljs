(ns mftickets-web.components.view-template-page.handlers
  (:require [mftickets-web.components.view-template-page.reducers :as reducers]))

(defn on-picked-template-change [{:keys [state]} template]
  (swap! state (reducers/on-picked-template template)))
