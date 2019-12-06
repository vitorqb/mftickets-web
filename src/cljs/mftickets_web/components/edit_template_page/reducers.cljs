(ns mftickets-web.components.edit-template-page.reducers)

(defn set-picked-template [x] #(assoc % :picked-template x))
(defn set-edited-template [x] #(assoc % :edited-template x))

(defn- set-loading? [x]
  {:pre [(boolean? x)]}
  #(assoc % :loading? x))

(defn- set-edited-template-submit-response
  [x]
  #(assoc % :edited-template-submit-response x))

(defn before-edited-template-submit
  []
  (comp (set-edited-template-submit-response nil)
        (set-loading? true)))

(defn after-edited-template-submit
  [response]
  (comp (set-edited-template-submit-response response)
        (set-loading? false)))
