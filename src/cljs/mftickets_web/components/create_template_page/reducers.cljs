(ns mftickets-web.components.create-template-page.reducers)

(defn set-edited-template [x] #(assoc % :edited-template x))
(defn set-create-template-response [x] #(assoc % :create-template-response x))

(defn set-is-loading? [x]
  {:pre [(boolean? x)]}
  #(assoc % :is-loading? x))
