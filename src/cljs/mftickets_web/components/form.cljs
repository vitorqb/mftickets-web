(ns mftickets-web.components.form)

(def form-base-class "form")

(defn form
  "A wrapper around a css form."
  [props & children]
  [:form {:class form-base-class}
   children])
