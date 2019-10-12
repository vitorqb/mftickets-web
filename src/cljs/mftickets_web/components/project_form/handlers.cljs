(ns mftickets-web.components.project-form.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as spec]
            [com.rpl.specter :as s]))

(defrecord InputChange [props input-spec]
  events.protocols/PEvent
  (propagate! [_]
    (let [{{:keys [EditedProjectChange->]} :events :project-form/keys [edited-project]} props
          {:keys [input-path input-value]} input-spec
          _ (assert (fn? EditedProjectChange->))
          _ (assert (spec/valid? (spec/or :keyword keyword?
                                          :col-of-keyword (spec/coll-of keyword?))
                                 input-path))]
      [(-> (s/setval input-path input-value edited-project)
           EditedProjectChange->)])))

(defrecord Submit [props]
  events.protocols/PEvent
  (propagate! [_]
    (let [Submit-> (-> props :events :Submit->)
          _ (assert (fn? Submit->))]
      [(Submit->)])))
