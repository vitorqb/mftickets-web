(ns mftickets-web.components.form)

(def form-wrapper-base-class
  "form-wrapper")

(def form-wrapper-loading-div-class
  (str form-wrapper-base-class "__loading-div"))

(def form-wrapper-loading-div-inactive-class-modifier
  (str form-wrapper-loading-div-class "--inactive"))

(defn- get-loading-div-class
  "Defines the loading div class given the props."
  [{:keys [is-loading?]}]
  (cond-> [form-wrapper-loading-div-class]
    (not is-loading?) (conj form-wrapper-loading-div-inactive-class-modifier)))

(defn loading-div
  "A div that overrides the form wrapper when loading."
  [props]
  [:div {:class (get-loading-div-class props)}
   "Loading..."])

(defn form
  "A wrapper around a css form."
  [props & children]
  [:div {:class form-wrapper-base-class}
   [loading-div props]
   [:form children]])
