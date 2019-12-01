(ns mftickets-web.components.edit-template-page.handlers
  (:require [mftickets-web.components.edit-template-page.reducers :as reducers]))

(defn on-template-picked [{:keys [state]} template]
  (swap! state (comp (reducers/set-edited-template template)
                     (reducers/set-picked-template template))))

(defn on-edited-template-change [{:keys [state]} new-template]
  (swap! state (reducers/set-edited-template new-template)))
