(ns mftickets-web.components.life-prober.reducers)

(defn set-status
  [new-status]
  #(assoc % :status new-status))
