(ns mftickets-web.components.login-page
  (:require
   [mftickets-web.components.login-page.queries :as queries]
   [mftickets-web.components.login-page.handlers :as handlers]
   [mftickets-web.components.login-page.reducers :as reducers]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.form :as components.form]
   [mftickets-web.components.message-box :as components.message-box]
   [mftickets-web.events :as events]))

;; Helpers
(defn- get-form-submit-handler
  "Returns a handler for the form submit depending on current props."
  [{:keys [state] :as props}]
  (let [handler (if (queries/email-has-been-submited-sucessfully? @state)
                  handlers/on-key-submit
                  handlers/on-email-submit)]
    (handler props)))

;; Components
(defn- email-input
  "An input for an email."
  [{:keys [state] :as props}]
  [components.input/input
   {:input/label "Email"
    :input/value (-> @state queries/email-input-state :value)
    :input/disabled (queries/email-has-been-submited-sucessfully? @state)
    :input.messages/on-change #(handlers/on-email-input-change props %)}])

(defn- key-input
  "And input for the key."
  [{:keys [state] :as props}]
  (when (queries/email-has-been-submited-sucessfully? @state)
    [components.input/input
     {:input/label "Key"
      :input/value (-> @state queries/key-input-state :value)
      :input.messages/on-change #(handlers/on-key-input-change props %)
      :parent-props props}]))

(defn- form
  "A form for the email and key inputs."
  [{:keys [state] :as props} & children]
  [components.form/form
   {:is-loading? (-> @state queries/email-submission-current-state #{:ongoing} boolean)
    :button-text "Submit!"
    :on-submit #(get-form-submit-handler props)}
   children])

(defn- msg-box
  "A message box with information for the user."
  [{:keys [state]}]
  (if-let [message (queries/user-message @state)]
    [components.message-box/message-box
     {:message message}]))

(defn login-page
  "Initial page used for login."
  [props]
  [:div.login-page
   [:h3.heading-tertiary "Log in"]
   [form props
    ^{:key 1} [email-input props]
    ^{:key 2} [key-input props]]
   [msg-box props]])
