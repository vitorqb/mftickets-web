(ns mftickets-web.components.edit-template-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.edit-template-page.reducers :as reducers]))

(defrecord PickedTemplateChange [template]
  events.protocols/PEvent
  (reduce! [_] #(-> %
                    ((reducers/set-edited-template template))
                    ((reducers/set-picked-template template)))))
