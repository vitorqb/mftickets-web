(ns mftickets-web.components.templates-page.reducers)

(defn set-is-loading? [x] #(assoc % :is-loading? x))
(defn set-templates-http-response [x] #(assoc % :templates-http-response x))
(defn set-current-page [x] #(assoc % :current-page x))
(defn set-current-page-size [x] #(assoc % :current-page-size x))
