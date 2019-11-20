(ns mftickets-web.instances.header
  (:require
   [mftickets-web.components.header :as components.header]
   [mftickets-web.state :as state]
   [mftickets-web.events :as events]
   [mftickets-web.app.handlers :as handlers]))

(defn header-instance
  [{:keys [app-state http] :as injections}]
  [components.header/header
   {:header.messages/display-router #(handlers/display-router-dialog injections)
    :state   (state/->FocusedAtom app-state [::state])
    :http    http
    :events {:RefreshAppMetadata-> #(handlers/->FetchAppMetadataResponse injections)}
    :parent-props {:state app-state}}])

