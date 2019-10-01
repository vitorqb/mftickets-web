(ns mftickets-web.components.project-picker.handlers-test
  (:require [mftickets-web.components.project-picker.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-on-change->
  (testing "Dispatches to on-change-> after extracing value only"
    (let [on-change identity
          props {:events {:on-change-> on-change}}
          project {:id 99 :name "Foo"}
          new-option {:value project :label "Foo"}
          event (sut/on-change props new-option)]
      (is (= [project] (events.protocols/propagate! event))))))
