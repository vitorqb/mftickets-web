(ns mftickets-web.components.view-template-page.queries)

(defn picked-template
  "Returns the template picked by the user."
  [x]
  (:picked-template x))

(defn is-loading? [x] (:is-loading? x false))

(defn delete-template-response [x] (:delete-template-response x))
