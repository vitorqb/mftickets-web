(ns mftickets-web.components.template-sections-form
  (:require [mftickets-web.components.input :as components.input]))

(def base-class "template-sections-form")
(def label-class (str base-class "__label"))
(def inputs-container-class (str base-class "__inputs-container"))

(defn template-sections-form
  "A form for sections of a template"
  [props]
  [:div {:class base-class}
   [:span {:class label-class}
    "Sections"]
   [:div {:class inputs-container-class}
    "<sections here>"]])
