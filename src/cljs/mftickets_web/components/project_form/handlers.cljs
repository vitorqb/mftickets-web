(ns mftickets-web.components.project-form.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as spec]
            [com.rpl.specter :as s]))

(defrecord Submit [props]
  events.protocols/PEvent
  (propagate! [_]
    (let [Submit-> (-> props :events :Submit->)
          _ (assert (fn? Submit->))]
      [(Submit->)])))

(defn on-input-change
  "Handles an input on-change message."
  [{:project-form.messages/keys [on-edited-project-change] :project-form/keys [edited-project]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(fn? on-edited-project-change) (fn? update-value-fn)]}

  (->> new-value (update-value-fn edited-project) on-edited-project-change))
