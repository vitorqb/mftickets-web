(ns mftickets-web.components.projects-page.reducers)

(defn set-loading? [x] #(assoc % :loading? x))
(defn set-fetch-projects-response [x] #(assoc % :fetch-projects-response x))
