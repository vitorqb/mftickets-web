(ns mftickets-web.domain.sequences-test
  (:require [mftickets-web.domain.sequences :as sut]
            [cljs.test :refer-macros [is are deftest testing use-fixtures async]]))

(deftest test-move-back

  (testing "List"

    (let [seq '(1 2 3)]

      (testing "No element"
        (let [pred #{999}]
          (is (= seq (sut/move-back pred seq)))))
      
      (testing "First element"
        (let [pred #{1}]
          (is (= seq (sut/move-back pred seq)))))

      (testing "Middle element"
        (let [pred #{2}]
          (is (= '(2 1 3) (sut/move-back pred seq)))))

      (testing "Last element"
        (let [pred #{3}]
          (is (= '(1 3 2) (sut/move-back pred seq)))))))

  (testing "Vector"

    (let [seq [1 2 3]]

      (testing "First element"
        (let [pred #{1}]
          (is (= seq (sut/move-back pred seq))))

        (testing "Middle element"
          (let [pred #{2}]
            (is (= '(2 1 3) (sut/move-back pred seq)))))))))

(deftest test-move-forward

  (testing "List"

    (let [seq '(1 2 3)]

      (testing "No element"
        (let [pred #{999}]
          (is (= seq (sut/move-forward pred seq)))))
      
      (testing "First element"
        (let [pred #{1}]
          (is (= '(2 1 3) (sut/move-forward pred seq)))))

      (testing "Middle element"
        (let [pred #{2}]
          (is (= '(1 3 2) (sut/move-forward pred seq)))))

      (testing "Last element"
        (let [pred #{3}]
          (is (= '(1 2 3) (sut/move-forward pred seq)))))))

  (testing "Vector"

    (let [seq [1 2 3]]

      (testing "First element"
        (let [pred #{1}]
          (is (= [2 1 3] (sut/move-forward pred seq))))

        (testing "Middle element"
          (let [pred #{2}]
            (is (= '(1 3 2) (sut/move-forward pred seq)))))))))

(deftest test-update-order

  (testing "Empty"
    (is (nil? (sut/update-order nil))))

  (testing "Two long"
    (is (= [{:id 1 :order 0} {:id 2 :order 1}] (sut/update-order [{:id 1} {:id 2}])))))
