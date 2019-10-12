(ns mftickets-web.components.form
  (:require [cljs.spec.alpha :as spec]))

;; Css
(def form-wrapper-base-class
  "form-wrapper")

(def form-wrapper-loading-div-class
  (str form-wrapper-base-class "__loading-div"))

(def form-wrapper-loading-div-inactive-class-modifier
  (str form-wrapper-loading-div-class "--inactive"))

(def form-wrapper-submit-button-class
  (str form-wrapper-base-class "__submit-button"))

(def submit-button-styles #{:default :danger})
(spec/def ::submit-button-styles submit-button-styles)

(def form-wrapper-submit-button-container-class
  (str form-wrapper-base-class "__submit-button-container"))

;; Helpers
(defn- submit-button-style->class
  "Maps submit-button-styles -> array of css class."
  [style]
  {:pre [(spec/valid? ::submit-button-styles style)]
   :post [(spec/valid? (spec/nilable (spec/coll-of string?)) %)]}
  (let [base [form-wrapper-submit-button-class]
        modifiers (case style
                    :danger [(str form-wrapper-submit-button-class "--danger")]
                    :default nil)]
    (concat base modifiers)))

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

;; Components
(defn loading-div
  "A div that overrides the form wrapper when loading."
  [props]
  [:div {:class (get-loading-div-class props)}
   "Loading..."])

(defn submit-btn
  "A input of type submit for the form."
  [{:keys [button-text button-style]
    :or {button-style :default
         button-text "Submit!"}}]
  {:pre [(spec/valid? ::submit-button-styles button-style)]}

  [:div {:class [form-wrapper-submit-button-container-class]}
   [:input {:class (submit-button-style->class button-style)
            :type "submit"
            :value button-text}]])

(defn form
  "A wrapper around a css form."
  [{:keys [button-text] :as props} & children]
  [:div {:class form-wrapper-base-class}
   [loading-div props]
   [:form
    {:on-submit (on-submit-handler props)}
    children
    [submit-btn props]]])
