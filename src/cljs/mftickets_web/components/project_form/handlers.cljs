(ns mftickets-web.components.project-form.handlers
  (:require [cljs.spec.alpha :as spec]
            [com.rpl.specter :as s]))

(defn on-input-change
  "Handles an input on-change message."
  [{:project-form.messages/keys [on-edited-project-change] :project-form/keys [edited-project]}
   {:factories.input/keys [update-value-fn]}
   new-value]

  {:pre [(fn? on-edited-project-change) (fn? update-value-fn)]}

  (->> new-value (update-value-fn edited-project) on-edited-project-change))
