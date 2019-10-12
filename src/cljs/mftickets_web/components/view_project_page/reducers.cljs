(ns mftickets-web.components.view-project-page.reducers)

(defn set-picked-project [x] #(assoc % :picked-project x))

(defn set-delete-response [x] #(assoc % :delete-response x))

(defn set-loading? [x] #(assoc % :loading? x))

(defn after-delete-picked-project
  "Handles state reducing given a delete http response."
  [{:keys [success] :as response}]
  {:pre [(boolean? success)] :post [(fn? %)]}
  (cond-> (comp (set-loading? false)
                (set-delete-response response))
    success (comp (set-picked-project nil))))
