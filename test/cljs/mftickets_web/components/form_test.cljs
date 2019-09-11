(ns mftickets-web.components.form-test
  (:require [mftickets-web.components.form :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-get-loading-div-class

  (testing "Disactive if not loading"
    (let [props {}]
      (is (= [sut/form-wrapper-loading-div-class
              sut/form-wrapper-loading-div-inactive-class-modifier]
             (sut/get-loading-div-class props)))))

  (testing "Active if loading"
    (let [props {:is-loading? true}]
      (is (= [sut/form-wrapper-loading-div-class]
             (sut/get-loading-div-class props))))))
