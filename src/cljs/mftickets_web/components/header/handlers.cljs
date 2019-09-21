(ns mftickets-web.components.header.handlers)

(defn display-router
  "Handler for displaying the router dialog."
  [{:keys [messages]}]
  (let [display-router-dialog (:display-router-dialog messages)]
    (fn []
      (display-router-dialog))))
