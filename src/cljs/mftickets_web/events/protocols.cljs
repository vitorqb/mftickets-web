(ns mftickets-web.events.protocols
  (:require
   [cljs.core.async :as async]))

(defprotocol PEvent
  (reduce!      [this] "Returns a reducer for the current state.")
  (propagate!   [this] "Returns seq of PEvents to be dispatched to the parent handler.")
  (dispatch!    [this] "Returns seq of PEvents to be dispatched to the current handler.")
  (run-effects! [this] "Runs side effects. Returns channel with seq of PEvents."))

(extend-type object
  PEvent
  (propagate!   [_] [])
  (reduce!      [_] nil)
  (dispatch!    [_] [])
  (run-effects! [_] nil))
