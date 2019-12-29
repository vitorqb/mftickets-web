(ns mftickets-web.components.view-template-page.reducers)

(defn on-picked-template
  "Reducer for when a new template is pikced by the user."
  [x]
  #(assoc % :picked-template x))

(defn set-is-loading? [x] #(assoc % :is-loading? x)) 

(defn set-delete-template-response [x] #(assoc % :delete-template-response x))
