(ns mftickets-web.components.router-input.handlers-test
  (:require [mftickets-web.components.router-input.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.router-input.reducers :as reducers]))

(deftest test-InputKeyUp--enter

  (testing "Nil if not Enter key"
    (let [event (sut/->InputKeyUp--enter {} "FOO")]
      (is (nil? (events.protocols/propagate! event))))))


(deftest test-on-input-change
  (let [state (atom {})
        new-value "FOO"
        e-new-state (-> @state ((reducers/set-input-value new-value)))]
    (sut/on-input-change {:state state} new-value)
    (is (= e-new-state @state))))
