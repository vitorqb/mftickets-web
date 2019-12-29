(ns mftickets-web.components.input.handlers)

(defn on-key-down
  [{:input.messages/keys [on-key-down]} event]
  {:pre [(or (nil? on-key-down) (fn? on-key-down))]}
  (when on-key-down (-> event .-key on-key-down)))

(defn on-html-input-change
  [{:input.messages/keys [on-change]} event]
  {:pre [(fn? on-change)]}
  (-> event .-target .-value on-change))
