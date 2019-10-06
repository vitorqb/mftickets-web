(ns mftickets-web.components.create-project-page.reducers)

(defn set-raw-project
  "Sets the user-inputted project."
  [raw-project]
  #(assoc % :raw-project raw-project))

(defn set-loading? [x] #(assoc % :loading? x))
(defn set-create-project-response [x] #(assoc % :create-project-response x))
