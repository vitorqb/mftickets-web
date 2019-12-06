(ns mftickets-web.components.edit-template-page
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.components.template-picker :as components.template-picker]
            [mftickets-web.components.edit-template-page.handlers :as handlers]
            [mftickets-web.state :as state]
            [mftickets-web.components.edit-template-page.queries :as queries]
            [mftickets-web.components.edit-template-page.reducers :as reducers]
            [mftickets-web.components.template-form :as components.template-form]
            [mftickets-web.components.template-form.inputs :as components.template-form.inputs]
            [mftickets-web.components.message-box :as components.message-box]
            [mftickets-web.components.template-sections-form.input :as c.template-sections-form.input]
            [mftickets-web.components.template-properties-form.input :as c.template-properties-form.input]))

;; Css
(def base-class "edit-template-page")
(def template-picker-wrapper-class (str base-class "__template-picker-wrapper"))
(def template-picker-contents-class (str base-class "__template-picker-contents"))
(def loading-wrapper-class (str base-class "__loading-wrapper"))

;; Specs
(spec/def :edit-template-page/project-id (spec/or :nil nil? :int int?))

(spec/def :edit-template-page.events/EditedTemplateChange-> fn?)
(spec/def :edit-template-page/events
  (:req-un [:edit-template-page.events/EditedTemplateChange->]))

(spec/def :edit-template-page/props
  (spec/keys
   :req [:edit-template-page/project-id]
   :opt-un [:edit-template-page/events]))

;; Helpers
(def template-form-inputs
  [components.template-form.inputs/id
   components.template-form.inputs/name
   (assoc components.template-form.inputs/project-id :input/disabled true)
   components.template-form.inputs/creation-date
   components.template-form.inputs/sections])

;; Components
(defn- loading-wrapper
  [{:keys [state]}]
  (when (queries/loading? @state)
    [:div {:class [loading-wrapper-class]} "Loading..."]))

(defn- message-box
  [{:keys [state] :as props}]
  (when-let [message (queries/user-message @state)]
    [components.message-box/message-box message]))

(defn- template-picker
  "A wrapper around `template-picker` for the user to select a template to edit."
  [{:keys [http state] :edit-template-page/keys [project-id] :as props}]

  {:pre [(do (spec/assert :edit-template-page/project-id project-id) true)]}

  (let [props {:template-picker/project-id project-id
               :template-picker/picked-template (queries/picked-template @state)
               :template-picker.messages/on-template-picked #(handlers/on-template-picked props %)
               :state (state/->FocusedAtom state ::template-picker)
               :http http}]

    [components.template-picker/template-picker props]))

(defn- template-form
  "A wrapper around `template-form` for the user to edit the template."
  [{:keys [state] :as props}]
  (if-let [picked-template (queries/picked-template @state)]
    (let [props {:template-form/edited-template (queries/edited-template @state)
                 :template-form/original-template picked-template
                 :template-form/inputs-metadatas template-form-inputs
                 :template-form.messages/on-edited-template-change
                 #(handlers/on-edited-template-change props %)
                 :template-form.messages/on-edited-template-submit
                 #(handlers/on-edited-template-submit props)}]
      [components.template-form/template-form props])))

(defn edit-template-page
  [{:keys [state] :as props}]

  {:pre [(spec/assert :edit-template-page/props props)]}

  [:div {:class base-class}

   [:h3.heading-tertiary "EDIT TEMPLATE"]

   [:div {:class template-picker-wrapper-class}
    [loading-wrapper props]
    [:span.featured-label-1 "Pick a template"]
    [:div {:class template-picker-contents-class}
     [template-picker props]]]
   [message-box props]
   [template-form props]])
