(ns mftickets-web.components.dialog.queries)

(defn disabled? [state]
  (get state :disabled? true))
