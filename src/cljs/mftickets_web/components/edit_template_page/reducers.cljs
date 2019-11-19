(ns mftickets-web.components.edit-template-page.reducers)

(defn set-picked-template [x] #(assoc % :picked-template x))
(defn set-edited-template [x] #(assoc % :edited-template x))
