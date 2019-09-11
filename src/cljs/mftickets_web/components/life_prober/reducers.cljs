(ns mftickets-web.components.life-prober.reducers)

(defn set-status
  [new-status]
  #(assoc % :status new-status))

(defn after-ping
  [{:keys [success]}]
  (set-status (if success :live :dead)))

(defn before-ping
  []
  (set-status :unknown))
