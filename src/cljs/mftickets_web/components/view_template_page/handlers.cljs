(ns mftickets-web.components.view-template-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.view-template-page.reducers :as reducers]))

(defrecord PickedTemplateChange [template]
  ;; Represents an event being picked by the user.
  events.protocols/PEvent
  (reduce! [_] (reducers/on-picked-template template)))
