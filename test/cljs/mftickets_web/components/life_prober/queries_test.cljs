(ns mftickets-web.components.life-prober.queries-test
  (:require [mftickets-web.components.life-prober.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.life-prober.reducers :as reducers]))

(deftest test-status

  (testing "Empty"
    (let [state {}
          queried (sut/status state)]
      (is (= :unknown queried))))

  (testing "Not Empty"
    (let [state (-> {} ((reducers/set-status :live)))
          queried (sut/status state)]
      (is (= :live queried)))))

