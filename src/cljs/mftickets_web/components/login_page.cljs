(ns mftickets-web.components.login-page
  (:require
   [mftickets-web.components.login-page.queries :as queries]
   [mftickets-web.components.login-page.handlers :as handlers]
   [mftickets-web.components.login-page.reducers :as reducers]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.form :as components.form]))

(defn- email-input
  "An input for an email."
  [{:keys [state reduce!]}]
  [components.input/input
   {:label "Email"
    :value (-> state queries/email-input-state :value)
    :on-change #(reduce! (reducers/set-email-value %))
    :disabled (queries/email-has-been-submited-sucessfully? state)}])

(defn- key-input
  "And input for the key."
  [{:keys [state reduce!]}]
  (when (queries/email-has-been-submited-sucessfully? state)
    [components.input/input
     {:label "Key"
      :value (-> state queries/key-input-state :value)
      :on-change #(reduce! (reducers/set-key-value %))}]))

(defn- form
  "A form for the email and key inputs."
  [{:keys [state] :as props} & children]
  [components.form/form
   {:is-loading? (-> state queries/email-submission-current-state #{:ongoing} boolean)
    :button-text "Submit!"
    :on-submit (handlers/email-submit props)}
   children])

(defn login-page
  "Initial page used for login."
  [{:keys [state reduce!] :as props}]
  [:div.login-page
   [:h3.heading-tertiary "Log in"]
   [form props
    ^{:key 1} [email-input props]
    ^{:key 2} [key-input props]]])
