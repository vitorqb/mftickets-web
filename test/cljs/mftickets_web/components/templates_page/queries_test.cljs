(ns mftickets-web.components.templates-page.queries-test
  (:require [mftickets-web.components.templates-page.queries :as sut]
            [mftickets-web.components.templates-page.reducers :as reducers]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [com.rpl.specter :as s]))

(deftest test-is-loading
  (is (true? (-> {} ((reducers/set-is-loading? true)) sut/is-loading?))))

(deftest test-templates

  (testing "Null if failed request"
    (let [response {:success false :body {:items [::foo]}}
          state (-> {} ((reducers/set-templates-http-response response)) atom)]
      (is (nil? (sut/templates @state)))))

  (testing "Null if no response"
    (let [state (atom {})]
      (is (nil? (sut/templates @state)))))

  (testing "Not null"
    (let [response {:success true :body {:items [::foo]}}
          state (-> {} ((reducers/set-templates-http-response response)) atom)]
      (is (= [::foo] (sut/templates @state))))))

(deftest test-current-page

  (testing "Set and get with reducer"
    (is (= 3 (-> {} ((reducers/set-current-page 3)) sut/current-page))))

  (testing "Defaults to 1"
    (is (= 1 (sut/current-page {})))))

(deftest test-current-page-size

  (testing "Set and get with reducer"
    (is (= 3 (-> {} ((reducers/set-current-page-size 3)) sut/current-page-size))))

  (testing "Defaults to 50"
    (is (= 50 (sut/current-page-size {})))))

(deftest test-page-count

  (let [current-page-size 3
        total-items-count 10
        response {:body {:total-items-count total-items-count}}
        state (-> {}
                  ((reducers/set-current-page-size current-page-size))
                  ((reducers/set-templates-http-response response)))]

    (testing "No response yet"
      (let [state* (-> state ((reducers/set-templates-http-response nil)))]
        (is (nil? (sut/page-count state*)))))

    (testing "Failed response"
      (let [response* (s/setval [:body :total-items-count] s/NONE response)
            state* (-> state ((reducers/set-templates-http-response response*)))]
        (is (nil? (sut/page-count state*)))))

    (testing "Success response"
      (is (= 4 (sut/page-count state))))))
