(ns mftickets-web.components.input.handlers)

(defn on-key-up
  [{:input.messages/keys [on-key-up]} event]
  {:pre [(or (nil? on-key-up) (fn? on-key-up))]}
  (when on-key-up (-> event .-key on-key-up)))

(defn on-html-input-change
  [{:input.messages/keys [on-change]} event]
  {:pre [(fn? on-change)]}
  (-> event .-target .-value on-change))
