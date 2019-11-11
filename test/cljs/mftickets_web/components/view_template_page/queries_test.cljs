(ns mftickets-web.components.view-template-page.queries-test
  (:require [mftickets-web.components.view-template-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.view-template-page.reducers :as reducers]))

(deftest test-picked-template

  (testing "Empty"
    (is (nil? (sut/picked-template {}))))

  (testing "Not empty"
    (let [picked-template {:id 1}
          state (-> {} ((reducers/on-picked-template picked-template)))]
      (is (= picked-template (sut/picked-template state))))))
