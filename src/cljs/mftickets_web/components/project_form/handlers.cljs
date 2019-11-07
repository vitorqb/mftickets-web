(ns mftickets-web.components.project-form.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as spec]
            [com.rpl.specter :as s]))

(defrecord InputChange [props metadata new-value]
  events.protocols/PEvent
  (propagate! [_]
    (spec/assert :project-form/input metadata)
    (let [{{:keys [EditedProjectChange->]} :events :project-form/keys [edited-project]} props
          {:project-form.input/keys [assoc-project-value-fn]} metadata
          new-edited-project (assoc-project-value-fn edited-project new-value)]
      [(EditedProjectChange-> new-edited-project)])))

(defrecord Submit [props]
  events.protocols/PEvent
  (propagate! [_]
    (let [Submit-> (-> props :events :Submit->)
          _ (assert (fn? Submit->))]
      [(Submit->)])))
