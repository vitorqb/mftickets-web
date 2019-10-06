(ns mftickets-web.components.select.handlers-test
  (:require [mftickets-web.components.select.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-on-change

  (testing "Propagates to on-change-> after transforming to cljs"
    (let [on-change identity
          props {:events {:on-change-> on-change}}
          new-value {:name "foo" :id 1}
          event (sut/on-change props (clj->js new-value))]
      (is (= [new-value] (events.protocols/propagate! event))))))
