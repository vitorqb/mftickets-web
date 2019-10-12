(ns mftickets-web.components.router-input.handlers-test
  (:require [mftickets-web.components.router-input.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-InputKeyUp--enter

  (testing "Nil if not Enter key"
    (let [event (sut/->InputKeyUp--enter {} "FOO")]
      (is (nil? (events.protocols/propagate! event))))))

