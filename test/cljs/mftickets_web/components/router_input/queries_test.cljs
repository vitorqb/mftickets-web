(ns mftickets-web.components.router-input.queries-test
  (:require [mftickets-web.components.router-input.queries :as sut]
            [mftickets-web.components.router-input.reducers :as reducers]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-history

  (testing "No history"
    (let [state {} props {:state state}]
      (is (= [] (sut/selection-history state)))))

  (testing "With history"
    (let [state (-> {} ((reducers/append-to-selection-history "Foo")))
          props {:state state}]
      (is (= ["Foo"] (sut/selection-history state))))))

