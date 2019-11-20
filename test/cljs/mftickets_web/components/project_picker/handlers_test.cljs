(ns mftickets-web.components.project-picker.handlers-test
  (:require [mftickets-web.components.project-picker.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-select-change
  (testing "Dispatches to on-picked-project-change after extracing value only"
    (let [on-picked-project-change (fn [x] [::on-picked-project-change x])
          props {:project-picker.messages/on-picked-project-change on-picked-project-change}
          project {:id 99 :name "Foo"}
          new-option {:value project :label "Foo"}]
      (is (= [::on-picked-project-change project]
             (sut/on-select-change props new-option))))))
