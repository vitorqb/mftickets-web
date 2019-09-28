(ns mftickets-web.components.input-test
  (:require [mftickets-web.components.input :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-label-span

  (testing "With nil"
    (is (= nil
           (sut/label-span nil))))

  (testing "With value"
    (is (= [:span {:class sut/base-input-wrapper-label-class} "FOO"]
           (sut/label-span "FOO")))))


(deftest html-input

  (testing "Base"
    (is (not (nil? (sut/html-input {}))))))
