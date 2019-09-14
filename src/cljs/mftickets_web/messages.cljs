(ns mftickets-web.messages
  (:require
   [cljs.pprint]))

(defn m-update-token [token] #(assoc % :token token))

(defn messages-getter
  "Prepares a lookable object for message functions.
  `messages-fns` is a map where each value is a function returning a reducer.  
  Everytime the getter returns a value, it reduces the app-state using the result
  of calling the reducer."
  [messages-fns app-state]

  (reify
    ILookup
    (-lookup [this k]
      (-lookup this k nil))

    (-lookup [this k not-found]
      (if-let [f (get messages-fns k)]
        (fn wrapped-f [& args] (swap! app-state (apply f args)))
        not-found))))
