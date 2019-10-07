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

(deftest test-form-wrapper-submit-button-modifiers
  (testing "Danger"
    (is (= [sut/form-wrapper-submit-button-class
            (str sut/form-wrapper-submit-button-class "--danger")]
           (sut/submit-button-style->class :danger)))))

(deftest test-submit-handler

  (testing "Calls preventDefault"
    (let [called? (atom false)
          event (clj->js {:preventDefault #(reset! called? true)})
          handler (sut/on-submit-handler {})]
      (handler event)
      (is (= true @called?))))

  (testing "Calls on-submit"
    (let [on-submit (constantly ::foo)
          event (clj->js {:preventDefault (constantly nil)})
          handler (sut/on-submit-handler {:on-submit on-submit})]
      (is (= ::foo (handler event))))))
