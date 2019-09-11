(ns mftickets-web.components.life-prober.reducers-test
  (:require [mftickets-web.components.life-prober.reducers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.life-prober.queries :as queries]))

(deftest test-set-status
  (let [state {}
        reducer (sut/set-status :live)
        new-state (reducer state)]
    (is (= :live (queries/status new-state)))))

(deftest test-before-ping

  (testing "Base"
    (let [state {}
          reducer (sut/before-ping)
          new-state (reducer state)]
      (is (= :unknown (queries/status new-state))))))

(deftest test-after-ping

  (testing "Live"
    (let [state {}
          reducer (sut/after-ping {:success true})
          new-state (reducer state)]
      (is (= :live (queries/status new-state)))))

  (testing "Dead"
    (let [state {}
          reducer (sut/after-ping {:success false})
          new-state (reducer state)]
      (is (= :dead (queries/status new-state))))))
