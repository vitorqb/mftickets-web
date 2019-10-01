(ns mftickets-web.events
  (:require
   [mftickets-web.events.protocols :as p]
   [cljs.core.async :as async]))

(defonce print? (atom false))

(defn- console-log [x] (when @print? (js/console.log x)))

(defn react!
  "Reacts to an event."
  [{:keys [state parent-props!] :as props} event]
  {:pre [(or (nil? event) (satisfies? p/PEvent event))
         (or (satisfies? IAtom state) (satisfies? ISwap state) (nil? state))]}

  (let [effects-to-call (atom [])]

    (loop [event* event
           props* props
           todo   []]
      
      (console-log {:event* event* :props* props* :todo todo})

      (cond

        ;; Base
        (and (nil? event*) (empty? todo))
        nil

        ;; No event but todo
        (nil? event*)
        (let [[[event** props**] & todo**] todo]
          (recur event** props** todo**))

        ;; Events
        :else
        (do

          ;; Save thunkified run-events
          (swap! effects-to-call conj [#(p/run-effects! event*) props*])

          ;; Reduces the component's state.
          (when-let [reducer (p/reduce! event*)]
            (console-log "Reducing with")
            (console-log reducer)
            (swap! (:state props*) reducer))

          ;; Propagates and dispatches
          (let [dispatch
                (map vector (p/dispatch! event*) (repeat props*))
                propagate
                (map vector (p/propagate! event*) (-> props* :parent-props repeat))]
            (recur nil nil (concat dispatch propagate todo))))))

    (doseq [[f props*] @effects-to-call
            :let [effects-chan (f)]
            :when effects-chan]
      (console-log "Effects channel:")
      (console-log effects-chan)
      (async/go
        (doseq [event* (async/<! effects-chan)]
          (console-log "Received event from channel:")
          (console-log event*)
          (react! props* event*))))))
