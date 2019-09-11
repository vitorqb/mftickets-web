(ns mftickets-web.components.input-test
  (:require [mftickets-web.components.input :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-on-change-handler

  (testing "Nothing if no on-change"
    (let [props {}
          handler (sut/on-change-handler props)
          event (clj->js {:target {:value "FOO"}})]
      (is (nil? (handler event)))))

  (testing "Extracts value, calls on-change with it and returns."
    (let [on-change identity
          props {:on-change on-change}
          handler (sut/on-change-handler props)
          event (clj->js {:target {:value "FOO"}})]
      (is (= "FOO" (handler event))))))

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
