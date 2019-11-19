(ns mftickets-web.components.template-form.handlers-test
  (:require [mftickets-web.components.template-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-on-input-change
  (let [edited-template {:id 999 :name "Foo"}
        update-value-fn #(assoc %1 :name %2)
        on-edited-template-change #(do [::edited-template-change %])
        props {:template-form/edited-template edited-template
               :template-form.messages/on-edited-template-change on-edited-template-change}
        metadata {:factories.input/update-value-fn update-value-fn}
        new-value "Bar"]
    (is (= [::edited-template-change (assoc edited-template :name new-value)]
           (sut/on-input-change props metadata new-value)))))
