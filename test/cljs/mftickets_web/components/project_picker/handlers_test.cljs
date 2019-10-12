(ns mftickets-web.components.project-picker.handlers-test
  (:require [mftickets-web.components.project-picker.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-Change
  (testing "Dispatches to Change-> after extracing value only"
    (let [Change-> identity
          props {:events {:Change-> Change->}}
          project {:id 99 :name "Foo"}
          new-option {:value project :label "Foo"}
          event (sut/->Change props new-option)]
      (is (= [project] (events.protocols/propagate! event))))))
