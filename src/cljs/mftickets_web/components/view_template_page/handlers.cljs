(ns mftickets-web.components.view-template-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.view-template-page.reducers :as reducers]))

(defn on-picked-template-change [{:keys [state]} template]
  (swap! state (reducers/on-picked-template template)))
