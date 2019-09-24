(ns mftickets-web.components.router-input.reducers-test
  (:require [mftickets-web.components.router-input.reducers :as sut]
            [mftickets-web.components.router-input.queries :as queries]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-set-input-value
  (is (= {:input-value "Foo"} ((sut/set-input-value "Foo") {}))))

(deftest test-append-to-selection-history
  (is (= {:selection-history ["Foo"]}
         ((sut/append-to-selection-history "Foo") {}))))

(deftest test-select-next
  (let [matching-options ["Foo" "Bar"]
        state (-> {} ((sut/append-to-selection-history "Foo")))
        reducer (sut/select-next matching-options)
        new-state (reducer state)]
    (is (= ["Bar" "Foo"]
           (queries/selection-history new-state)))))

(deftest test-select-previous
  (let [matching-options ["Foo" "Bar"]
        state (-> {} ((sut/append-to-selection-history "Bar")))
        reducer (sut/select-previous matching-options)
        new-state (reducer state)]
    (is (= ["Foo" "Bar"]
           (queries/selection-history new-state)))))

(deftest test-select-from-key

  (testing "ArrowUp"
    (with-redefs [sut/select-previous identity]
      (is (= (sut/select-from-key ::foo "ArrowUp") ::foo))))

  (testing "ArrowDown"
    (with-redefs [sut/select-next identity]
      (is (= (sut/select-from-key ::foo "ArrowDown") ::foo))))

  (testing "None"
    (is (= (sut/select-from-key ::foo "UNKOWN") identity))))
