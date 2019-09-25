(ns mftickets-web.components.table-test
  (:require [mftickets-web.components.table :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-th

  (is (= [:th {:class [sut/th-base-class]} "Foo"]
         (sut/th {:table/th-value "Foo"}))))

(deftest test-thead

  (let [th-values ["Foo" "Bar"]
        thead (sut/thead {:table/th-values th-values})]

    (testing "Renders thead with class"
      (is (= :thead (first thead)))
      (is (= [sut/thead-base-class] (-> thead second :class))))

    (testing "Renders tr with classes"
      (is (= :tr (get-in thead [2 0])))
      (is (= [sut/tr-base-class sut/tr-header-modifier] (get-in thead [2 1 :class]))))

    (testing "Renders th"
      (let [ths (get-in thead [2 2])]
        (is (= 2 (count ths)))
        (is (= sut/th (-> ths first first)))
        (is (= "Foo"  (-> ths first second :table/th-value)))))))

(deftest test-extract-th-values

  (testing "Empty"
    (is (= [] (sut/extract-th-values {})))
    (is (= [] (sut/extract-th-values {:table/config []}))))

  (testing "Base"
    (let [config [{:table/header "Foo"} {:table/header "Bar"}]]
      (is (= ["Foo" "Bar"]
             (sut/extract-th-values {:table/config config}))))))
