(ns mftickets-web.components.template-picker
  (:require
   [cljs.spec.alpha :as spec]
   [mftickets-web.components.select :as components.select]
   [mftickets-web.components.template-picker.handlers :as handlers]
   [cljs.core.async :as async]
   [mftickets-web.components.message-box :as components.message-box]))

;; Specs
(spec/def :template-picker.http/get-matching-templates
  fn?)

(spec/def :template-picker/http
  #(-> % :get-matching-templates fn?))

(spec/def :template-picker.messages/on-template-picked ifn?)

(spec/def :template-picker/project-id (spec/nilable int?))

(spec/def :template-picker/picked-template any?)

(spec/def :template-picker/props
  (spec/keys :req-un [:template-picker/http]
             :req [:template-picker/project-id
                   :template-picker/picked-template
                   :template-picker.messages/on-template-picked]))

;; Globals
(def ^:private no-project-selected-user-message "No project is selected!")

;; Helpers
(defn- template->select-option [{:keys [name] :as template}] {:label name :value template})

(defn- get-matching-options
  "Returns a function that can be used to get matching options.
  Simply adapts `get-matching-templates` to `get-matching-options`."
  [{{:keys [get-matching-templates]} :http :template-picker/keys [project-id]}]
  (fn i-get-matching-options [input-value]
    (async/go
      (->> {:project-id project-id :name-like input-value}
           get-matching-templates
           async/<!
           (map template->select-option)))))

;; Component
(defn- no-project-selected-message-box
  "A message box displayed when no component is selected."
  []
  (let [message-opts {:message no-project-selected-user-message}]
    (components.message-box/message-box message-opts)))

(defn- async-select
  "Wrapper around async-select to be used by this component."
  [{:keys [state http] :template-picker/keys [project-id picked-template] :as props}]
  (if-not project-id
    [no-project-selected-message-box]
    (let [picked-option (some-> picked-template template->select-option)
          props* {:parent-props props
                  :select/value picked-option
                  :select.async/get-matching-options (get-matching-options props)
                  :select.messages/on-select-change #(handlers/on-select-change props %)}]
      [components.select/async-select props*])))

(defn template-picker
  "A Select component meant for the user to pick a template."
  [props]
  {:pre [(spec/assert :template-picker/props props)]}
  [:div
   [async-select props]])
