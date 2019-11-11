(ns mftickets-web.components.template-picker.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as spec]))

(defrecord ValueChange [props picked-option]
  ;; Represents the user selecting one of the templates.
  events.protocols/PEvent
  (propagate! [_]
    (let [{{:keys [ValueChange->]} :events} props
          _ (spec/assert fn? ValueChange->)
          picked-template (:value picked-option)]
      [(ValueChange-> picked-template)])))
