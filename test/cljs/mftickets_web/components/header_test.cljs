(ns mftickets-web.components.header-test
  (:require
   [mftickets-web.components.header :as sut]
   [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-life-prober

  (testing "Passes state"
    (let [state {::sut/life-prober {::foo ::bar}}
          props {:state (atom state)}
          life-prober-state (-> props sut/life-prober second :state deref)]
      (is (= {::foo ::bar} life-prober-state)))))
