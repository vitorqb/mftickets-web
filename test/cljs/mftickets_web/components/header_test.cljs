(ns mftickets-web.components.header-test
  (:require [mftickets-web.components.header :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-life-prober

  (testing "Passes state"
    (let [state {::sut/life-prober {::foo ::bar}}
          props {:state state}
          life-prober-state (-> props sut/life-prober second :state)]
      (is (= {::foo ::bar} life-prober-state))))

  (testing "Correctly passes reduce!"
    (let [reduce! (fn [f] (f {}))
          props {:reduce! reduce!}
          life-prober-reduce! (-> props sut/life-prober second :reduce!)]
      (is (= {::sut/life-prober {::foo ::bar}}
             (life-prober-reduce! (constantly {::foo ::bar})))))))


(deftest test-router-btn

  (testing "Calls display-router-dialog."
    (let [messages {:display-router-dialog (constantly ::foo)}
          props {:messages messages}
          on-click (-> props sut/router-btn second :on-click)]
      (is (= ::foo (on-click))))))
