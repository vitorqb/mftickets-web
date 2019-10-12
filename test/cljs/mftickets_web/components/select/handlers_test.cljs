(ns mftickets-web.components.select.handlers-test
  (:require [mftickets-web.components.select.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-Change

  (testing "Propagates to Change-> after transforming to cljs"
    (let [Change-> identity
          props {:events {:Change-> Change->}}
          new-value {:name "foo" :id 1}
          event (sut/->Change props (clj->js new-value))]
      (is (= [new-value] (events.protocols/propagate! event))))))
