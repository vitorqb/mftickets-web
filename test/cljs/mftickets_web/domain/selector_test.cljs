(ns mftickets-web.domain.selector-test
  (:require [mftickets-web.domain.selector :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))


(deftest get-selected-el-index

  (testing "No history and no options -> nil"
    (let [options [] history []]
      (is (nil? (sut/get-selected-el-index options history)))))

  (testing "With History"
    (let [history ["Foo"]]

      (testing "No options -> nil"
        (let [options []]
          (is (nil? (sut/get-selected-el-index options history)))))

      (testing "History with unkown option -> 0"
        (let [options ["Bar"]]
          (is (= 0 (sut/get-selected-el-index options history)))))

      (testing "History known option -> index"
        (let [options ["Bar" "Foo"]]
          (is (= 1 (sut/get-selected-el-index options history))))))))

(deftest test-select-next

  (testing "No history and no options -> nil"
    (is (nil? (sut/select-next [] []))))

  (testing "No options -> Nil"
    (is (nil? (sut/select-next [] ["Foo"]))))

  (testing "No history -> select second"
    (let [options ["Bar" "Foo"] history []]
      (is (= ["Foo"] (sut/select-next options history)))))

  (testing "With history and options, selects next option."
    (let [history ["Foo" "Bar"] options ["Bar" "Baz"]]
      (is (= ["Foo" "Bar" "Baz"]
             (sut/select-next options history)))))

  (testing "With history and options but no next option, do nothing"
    (let [history ["Foo"] options ["Bar" "Foo"]]
      (is (= history (sut/select-next options history))))))

(deftest test-select-previous

  (testing "No history and no options -> nil"
    (is (nil? (sut/select-previous [] []))))

  (testing "No options -> Nil"
    (is (nil? (sut/select-previous [] ["Foo"]))))

  (testing "No history -> identity"
    (let [options ["Bar" "Foo"] history []]
      (is (= [] (sut/select-previous options history)))))

  (testing "With history and options, selects previous option."
    (let [history ["Foo" "Bar"] options ["Foo" "Bar" "Baz"]]
      (is (= ["Foo" "Bar" "Foo"]
             (sut/select-previous options history)))))

  (testing "With history and options but no previous option, do nothing"
    (let [history ["Foo" "Bar"] options ["Bar" "Foo"]]
      (is (= history (sut/select-previous options history))))))
