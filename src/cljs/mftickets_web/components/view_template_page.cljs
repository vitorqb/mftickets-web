(ns mftickets-web.components.view-template-page
  (:require [mftickets-web.components.template-picker :as components.template-picker]
            [cljs.spec.alpha :as spec]
            [mftickets-web.state :as state]
            [mftickets-web.components.view-template-page.handlers :as handlers]
            [mftickets-web.components.view-template-page.queries :as queries]
            [mftickets-web.components.template-form :as components.template-form]
            [mftickets-web.components.template-form.inputs :as components.template-form.inputs]
            [mftickets-web.specs]))

;; Css
(def base-class "template-page")
(def template-picker-wrapper-class (str base-class "__template-picker-wrapper"))
(def template-picker-contents-class (str base-class "__template-picker-contents"))

;; Specs
(spec/def :view-template-page/props
  (spec/keys :req-un [::mftickets-web.specs/state]))

;; Metadata
(def template-form-inputs
  [components.template-form.inputs/id
   (assoc components.template-form.inputs/name :input/disabled true)
   (assoc components.template-form.inputs/project-id :input/disabled true)
   components.template-form.inputs/creation-date
   (assoc components.template-form.inputs/sections :template-sections-form/disabled true)])

;; Components
(defn template-picker
  "A wrapper around `template-picker` for the user to select a template to view."
  [{:keys [http state] :view-template-page/keys [project-id] :as props}]
  [components.template-picker/template-picker
   {:http http
    :state (state/->FocusedAtom state ::template-picker)
    :template-picker/project-id project-id
    :template-picker/picked-template (queries/picked-template @state)
    :template-picker.messages/on-template-picked #(handlers/on-picked-template-change props %)}])

(defn template-form
  "A wrapper around `template-form` for the user to see a template."
  [{:keys [state]}]
  (let [template (queries/picked-template @state)
        props* {:template-form/original-template template
                :template-form/edited-template template
                :template-form/inputs-metadatas template-form-inputs}]
    [components.template-form/template-form props*]))

(defn view-template-page
  [props]
  {:pre [(spec/assert :view-template-page/props props)]}

  [:div {:class base-class}

   [:h3.heading-tertiary "VIEW TEMPLATE"]

   [:div {:class template-picker-wrapper-class}
    [:span.featured-label-1 "Pick a template:"]
    [:div {:class template-picker-contents-class}
     [template-picker props]]]

   [template-form props]])
