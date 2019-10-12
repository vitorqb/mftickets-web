(ns mftickets-web.components.project-form.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as spec]
            [com.rpl.specter :as s]))

(defn on-input-change
  "Handler for a change of a input value."
  [{{:keys [EditedProjectChange->]} :events
    :project-form/keys [edited-project]}
   {:keys [input-path input-value]}]

  {:pre [(fn? EditedProjectChange->)
         (spec/valid? (spec/or :keyword keyword?
                               :col-of-keyword (spec/coll-of keyword?))
                   input-path)]}
  
  ^{::name "on-input-change"}
  (reify events.protocols/PEvent
    (propagate! [_] [(-> (s/setval input-path input-value edited-project)
                         EditedProjectChange->)])))

(defn on-submit
  "Handler for the form submission."
  [{{:keys [on-submit->]} :events}]
  {:pre [(fn? on-submit->)]}

  ^{::name "on-submit"}
  (reify events.protocols/PEvent
    (propagate! [_] [(on-submit->)])))
