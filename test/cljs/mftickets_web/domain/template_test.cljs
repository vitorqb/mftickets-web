(ns mftickets-web.domain.template-test
  (:require [mftickets-web.domain.template :as sut]
            [cljs.test :refer-macros [is are deftest testing use-fixtures async]]
            [mftickets-web.domain.kw :as domain.kw]))

(deftest test-set-value-types-to-keyword
  (let [template {:sections [{:properties [{:value-type ::foo}]}]}]
    (is (= template
           (sut/set-value-types-to-keyword
            (update-in template [:sections 0 :properties 0 :value-type] domain.kw/kw->str))))))

(deftest test-prepend-empty-section

  (testing "Empty"
    (is (= {:sections [{:id 1 :order 0}]}
           (sut/prepend-section {} {:id 1}))))

  (testing "Two long"
    (let [template {:id 2 :sections [{:id 1 :order 0} {:id 2 :order 1}]}
          exp-template {:id 2 :sections [{:id 3 :order 0} {:id 1 :order 1} {:id 2 :order 2}]}]
      (is (= exp-template (sut/prepend-section template {:id 3}))))))
