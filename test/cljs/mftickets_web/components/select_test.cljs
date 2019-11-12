(ns mftickets-web.components.select-test
  (:require [mftickets-web.components.select :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-label
  (let [value {:value 1 :label "1"}
        options [value]
        label "foo"
        label-wrapper-class "bar"
        props {:parent-props {}
               :events {:Change-> #(do)}
               :select/value value
               :select/options options
               :select/label label
               :select/label-wrapper-class label-wrapper-class}]

    (testing "Nil if no label"
      (let [props* (dissoc props :select/label)]
        (is (nil? (sut/label props*)))))

    (testing "With label..."
      (let [component (sut/label props)]

        (testing "Wrapper span with given label-wrapper-class"
          (is (= :span (first component)))
          (is (= label-wrapper-class (-> component second :class))))

        (testing "Inner label with base class"
          (let [inner-label (get component 2)]
            (is (= :span (first inner-label)))
            (is (= sut/base-label-class (-> inner-label second :class)))))))))
