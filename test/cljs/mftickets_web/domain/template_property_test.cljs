(ns mftickets-web.domain.template-property-test
  (:require [mftickets-web.domain.template-property :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-get-id

  (testing "Not new obj"
    (is (= 9 (sut/get-id {:id 9}))))

  (testing "New obj"
    (with-redefs [sut/gen-new-property-id (constantly 9)]
      (is (= 9 (-> (sut/gen-empty-template-property {}) sut/get-id))))))

(deftest test-same-id

  (testing "False if new and not new"
    (is (false? (sut/same-id? {:id 1} {::sut/new-obj-id "new-1"}))))

  (testing "Equal and new"
    (is (true? (sut/same-id? {::sut/new-obj-id 1} {::sut/new-obj-id 1}))))

  (testing "Equal and old"
    (is (true? (sut/same-id? {:id 1} {:id 1}))))

  (testing "Diff and New"
    (is (false? (sut/same-id? {::sut/new-obj-id 1} {::sut/new-obj-id 2}))))

  (testing "Diff and Old"
    (is (false? (sut/same-id? {:id 1} {:id 2})))))
