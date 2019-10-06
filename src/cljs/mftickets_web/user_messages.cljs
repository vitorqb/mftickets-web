(ns mftickets-web.user-messages
  (:require [cljs.spec.alpha :as spec]))

;; Specs
(def ^:private known-styles #{:success :error})
(spec/def :user-messages/style known-styles)
(spec/def :user-messages/message string?)
(spec/def :user-messages/style-and-message
  (spec/cat :style :user-messages/style :message :user-messages/message))

;; Messages
(def generic-error "Something went wrong! :(")
(def success "Done! :)")
