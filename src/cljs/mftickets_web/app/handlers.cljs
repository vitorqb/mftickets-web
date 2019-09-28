(ns mftickets-web.app.handlers
  (:require
   [accountant.core :as accountant]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]
   [mftickets-web.app.reducers :as reducers]))

(defn display-router-dialog
  "An event to display the router dialog."
  []
  ^{::name "display-router-dialog"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/display-router-dialog))))

(defn close-router-dialog
  "An events to hide the router dialog."
  []
  ^{::name "close-router-dialog"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/close-router-dialog))))

(defn update-token
  "An event to update the current token."
  [new-token]
  ^{::name "update-token"}
  (reify events.protocols/PEvent
    (reduce! [_] (reducers/set-token new-token))))

(defn navigate
  "An event to navigate to another page."
  [href]
  ^{::name "navigate"}
  (reify events.protocols/PEvent
    (run-effects! [_] (do (accountant/navigate! href) nil))))
