(ns mftickets-web.components.form)

(def form-wrapper-base-class
  "form-wrapper")

(def form-wrapper-loading-div-class
  (str form-wrapper-base-class "__loading-div"))

(def form-wrapper-loading-div-inactive-class-modifier
  (str form-wrapper-loading-div-class "--inactive"))

(def form-wrapper-submit-button-class
  (str form-wrapper-base-class "__submit-button"))

(def form-wrapper-submit-button-container-class
  (str form-wrapper-base-class "__submit-button-container"))

(defn- on-submit-handler
  "Returns a handler for on-submit given some props."
  [{:keys [on-submit]}]
  (fn [event]
    (.preventDefault event)
    (when on-submit
      (on-submit))))

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

(defn submit-btn
  "A input of type submit for the form."
  [{:keys [button-text]}]
  [:div {:class [form-wrapper-submit-button-container-class]}
   [:input {:class [form-wrapper-submit-button-class]
            :type "submit"
            :value (or button-text "Submit!")}]])

(defn form
  "A wrapper around a css form."
  [{:keys [button-text] :as props} & children]
  [:div {:class form-wrapper-base-class}
   [loading-div props]
   [:form
    {:on-submit (on-submit-handler props)}
    children
    [submit-btn props]]])
