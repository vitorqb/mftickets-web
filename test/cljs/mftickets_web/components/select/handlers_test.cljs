(ns mftickets-web.components.select.handlers-test
  (:require [mftickets-web.components.select.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-on-change

  (testing "Propagates to on-select-change after transforming to cljs"
    (let [on-select-change (fn [x] [::on-select-change x])
          props {:select.messages/on-select-change on-select-change}
          new-value {:name "foo" :id 1}]
      (is (= [::on-select-change new-value]
             (sut/on-change props new-value))))))

(deftest test-after-load-options

  (testing "passes matching-options to callback"
    (let [value {:foo 1}
          callback identity
          result (sut/after-load-options [value] callback)]
      (is (= [value] (js->clj result :keywordize-keys true))))))
