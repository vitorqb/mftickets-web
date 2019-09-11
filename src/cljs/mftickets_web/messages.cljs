(ns mftickets-web.messages)

(defn message-handler
  "Returns a message handler given an application state."
  [{:keys [app-state]}]
  (fn a-message-handler [msg & args]
    (cljs.pprint/pprint {:msg msg :args args})
    (case msg

      :update-token
      (let [token (first args)]
        (swap! app-state assoc :token token)))))
