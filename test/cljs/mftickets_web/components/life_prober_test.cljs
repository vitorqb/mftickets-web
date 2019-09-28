(ns mftickets-web.components.life-prober-test
  (:require [mftickets-web.components.life-prober :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.life-prober.queries :as queries]
            [mftickets-web.components.life-prober.reducers :as reducers]))

(deftest test-get-status-displayer-classes

  (testing "Base"
    (let [state (-> {} ((reducers/set-status :live)) atom)
          props {:state state}]
      (is (= [sut/status-displayer-class sut/status-displayer-class-live-modifier]
             (sut/get-status-displayer-classes props))))))
