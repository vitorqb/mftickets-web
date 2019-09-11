(ns mftickets-web.components.login-page
  (:require
   [mftickets-web.components.login-page.queries :as queries]
   [mftickets-web.components.input :as components.input]
   [mftickets-web.components.form :as components.form]))

(defn- email-input
  "An input for an email."
  []
  [components.input/input {:label "Email"}])

(defn- key-input
  "And input for the key."
  [{:keys [state]}]
  (when (queries/email-has-been-submited-sucessfully? state)
    [components.input/input {:label "Key"}]))

(defn- form
  "A form for the email and key inputs."
  [{:keys [state]} & children]
  [components.form/form
   {:is-loading? (-> state queries/email-submission-current-state #{:ongoing} boolean)}
   children])

(defn login-page
  "Initial page used for login."
  [{:keys [state reduce!] :as props}]
  [:div.login-page
   [:h3.heading-tertiary
    "Log in"]
   [form props
    ^{:key 1} [email-input]
    ^{:key 2} [key-input]]])
