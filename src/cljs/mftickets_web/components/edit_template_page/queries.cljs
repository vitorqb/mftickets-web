(ns mftickets-web.components.edit-template-page.queries)

(defn picked-template [x] (:picked-template x))
(defn edited-template [x] (:edited-template x))
(defn loading? [x] (:loading? x))
(defn edited-template-submit-response [x] (:edited-template-submit-response x))
