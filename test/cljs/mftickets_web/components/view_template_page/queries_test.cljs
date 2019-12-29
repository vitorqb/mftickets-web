(ns mftickets-web.components.view-template-page.queries-test
  (:require [mftickets-web.components.view-template-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.view-template-page.reducers :as reducers]))

(deftest test-picked-template

  (testing "Empty"
    (is (nil? (sut/picked-template {}))))

  (testing "Not empty"
    (let [picked-template {:id 1}
          state (-> {} ((reducers/on-picked-template picked-template)))]
      (is (= picked-template (sut/picked-template state))))))

(deftest test-is-loading?
  (is (true? (-> {} ((reducers/set-is-loading? true)) sut/is-loading?))))

(deftest test-delete-template-response
  (let [state (-> {} ((reducers/set-delete-template-response 1)))]
    (is (= 1 (sut/delete-template-response state)))))
