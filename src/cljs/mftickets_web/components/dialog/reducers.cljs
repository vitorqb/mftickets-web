(ns mftickets-web.components.dialog.reducers)

(defn set-disabled? [x]
  {:pre [(boolean? x)]}
  #(assoc % :disabled? x))
