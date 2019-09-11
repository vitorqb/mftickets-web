(ns mftickets-web.components.life-prober.queries)

(def possible-status #{:live :dead :unknown})

(defn status
  [state]
  (or (some-> state :status) :unknown))
