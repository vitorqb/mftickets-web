(ns mftickets-web.components.template-form.handlers
  (:require [mftickets-web.events.protocols :as events.protocols]
            [cljs.spec.alpha :as spec]))

(defrecord InputChange [props metadata new-value]
  ;; Represents an input change.
  ;; `props` -> template form props.
  ;; `metadata` -> inputs metadata for the input-like component.
  ;; `new-value` -> The new value

  events.protocols/PEvent
  (propagate! [_]

    (spec/assert :factories/input metadata)
    
    (let [{{:keys [EditedTemplateChange->]} :events :template-form/keys [edited-template]} props
          _ (spec/assert fn? EditedTemplateChange->)
          {:factories.input/keys [update-value-fn]} metadata
          new-edited-template (update-value-fn edited-template new-value)]

      [(EditedTemplateChange-> new-edited-template)])))
