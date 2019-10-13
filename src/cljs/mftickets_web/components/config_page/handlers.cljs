(ns mftickets-web.components.config-page.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as spec]))

(defrecord UpdateCurrentProject [props new-project]
  events.protocols/PEvent
  (propagate! [_]
    (let [UpdateCurrentProject-> (-> props :events :UpdateCurrentProject->)
          _ (spec/assert fn? UpdateCurrentProject->)]
      [(UpdateCurrentProject-> new-project)])))
