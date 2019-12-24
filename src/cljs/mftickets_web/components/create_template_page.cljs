(ns mftickets-web.components.create-template-page
  (:require [mftickets-web.components.template-form :as components.template-form]
            [cljs.spec.alpha :as spec]
            [mftickets-web.domain.template :as domain.template]
            [mftickets-web.components.create-template-page.queries :as queries]
            [mftickets-web.components.message-box :as components.message-box]
            [mftickets-web.components.create-template-page.handlers :as handlers]
            [mftickets-web.components.template-sections-form.input :as c.template-sections-form.input]
            [mftickets-web.components.template-properties-form.input :as c.template-properties-form.input]
            [mftickets-web.components.template-form.inputs :as components.template-form.inputs]
            [mftickets-web.components.factories.loading-wrapper :as c.factories.loading-wrapper :refer-macros [def-loading-wrapper]]))

;; Css
(def base-class "create-template-page")
(def loading-wrapper-class (str base-class "__loading-wrapper"))

;; Specs
(spec/def :create-template-page.current-project/id int?)
(spec/def :create-template-page/current-project
  (spec/keys
   :req-un [:create-template-page.current-project/id]))
(spec/def :create-template-page/properties-types (spec/nilable (spec/coll-of keyword?)))

;; Helpers
(def template-properties-form-inputs
  [c.template-properties-form.input/actions-buttons
   c.template-properties-form.input/id
   c.template-properties-form.input/name
   c.template-properties-form.input/is-multiple
   c.template-properties-form.input/value-type])

(def template-sections-form-inputs
  [c.template-sections-form.input/actions-buttons
   c.template-sections-form.input/id
   c.template-sections-form.input/name
   (assoc c.template-sections-form.input/properties
          :template-properties-form/inputs-metadatas template-properties-form-inputs)])

(def template-form-inputs
  [components.template-form.inputs/id
   components.template-form.inputs/name
   (assoc components.template-form.inputs/project-id :input/disabled true)
   components.template-form.inputs/creation-date
   components.template-form.inputs/sections-actions-buttons
   (assoc components.template-form.inputs/sections
          :template-sections-form/inputs-metadatas
          template-sections-form-inputs)])

;; Components
(def-loading-wrapper loading-wrapper queries/is-loading? loading-wrapper-class)

(defn- message-box [{:keys [state]}]
  (if-let [[style message] (queries/user-message @state)]
    [components.message-box/message-box {:style style :message message}]))

(defn- template-form
  [{:create-template-page/keys [current-project properties-types] :keys [state] :as props}]

  {:pre [(spec/assert :create-template-page/current-project current-project)
         (spec/assert :create-template-page/properties-types properties-types)
         (spec/assert :mftickets-web.specs/state state)]}
  
  (let [edited-template (queries/edited-template @state props)
        props* {:template-form/original-template
                nil

                :template-form/edited-template
                edited-template

                :template-form/inputs-metadatas
                template-form-inputs

                :template-form/properties-types
                properties-types

                :template-form.messages/on-edited-template-change
                #(handlers/on-new-template-change props %)

                :template-form.messages/on-edited-template-submit
                #(handlers/on-new-template-submit props)}]
    
    [components.template-form/template-form props*]))

(defn create-template-page
  [{:create-template-page/keys [current-project] :as props}]
  [:div {:class base-class}
   [loading-wrapper props]
   [:h3.heading-tertiary "Create Template Page!"]
   [message-box props]
   (if-not current-project
     [components.message-box/message-box {:message "No project is selected!"}]
     [:div
      [template-form props]])])
