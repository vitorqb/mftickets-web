(ns mftickets-web.components.templates-page.reducers)

(defn set-templates-http-response [x] #(assoc % :templates-http-response x))
