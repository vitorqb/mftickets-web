(ns mftickets-web.components.project-form.handlers-test
  (:require [mftickets-web.components.project-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-on-input-change
  (let [on-edited-project-change (fn [x] [::on-edited-project-change x])
        edited-project {:name "Foo"}
        props {:project-form.messages/on-edited-project-change on-edited-project-change
               :project-form/edited-project edited-project}
        new-value "Bar"
        metadata {:factories.input/update-value-fn #(assoc %1 :name %2)}
        new-edited-template {:name new-value}]
    (is (= [::on-edited-project-change new-edited-template]
           (sut/on-input-change props metadata new-value)))))
