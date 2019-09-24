(ns mftickets-web.events
  (:require
   [mftickets-web.events.protocols :as p]
   [cljs.core.async :as async]))

(defn react!
  "Reacts to an event."
  [{:keys [state parent-react!] :as props} event]
  {:pre [(or (nil? event) (satisfies? p/PEvent event))
         (or (satisfies? IAtom state) (satisfies? ISwap state) (nil? state))]}
  (js/console.log "Received event:")
  (js/console.log event)
  (when event
    
    ;; Reduces the component's state.
    (when-let [reducer (p/reduce! event)]
      (js/console.log "Reducing with")
      (js/console.log reducer)
      (swap! state reducer))

    ;; Dispatches events to current handler
    (doseq [event* (p/dispatch! event)]
      (js/console.log "Dispatching event")
      (js/console.log event*)
      (react! props event*))

    ;; Propagates events to parent
    (doseq [event* (p/propagate! event)]
      (js/console.log "Propagating event")
      (js/console.log event*)
      (parent-react! event*))

    ;; Run effects
    (when-let [effects-chan (p/run-effects! event)]
      (js/console.log "Effects channel:")
      (js/console.log effects-chan)
      (async/go
        (doseq [event* (async/<! effects-chan)]
          (js/console.log "Received event from channel:")
          (js/console.log event*)
          (react! props event*))))))
