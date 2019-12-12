(ns mftickets-web.components.templates-page.handlers-test
  (:require [mftickets-web.components.templates-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.templates-page.reducers :as reducers]
            [mftickets-web.components.templates-page.queries :as queries]))

(deftest test-page-in-acceptable-range

  (let [total-items-count 10
        page-size 2
        response {:body {:total-items-count total-items-count}}
        state (-> {}
                  ((reducers/set-templates-http-response response))
                  ((reducers/set-current-page-size page-size))
                  atom)
        props {:state state}
        page-count (queries/page-count @state)
        _ (assert int? page-count)]

    (testing "False if 0"
      (is (false? (sut/page-in-acceptable-range props 0))))

    (testing "False if higher than page-count"
      (is (false? (sut/page-in-acceptable-range props (inc page-count)))))

    (testing "True if = page-count"
      (is (true? (sut/page-in-acceptable-range props page-count))))

    (testing "True if = 1"
      (is (true? (sut/page-in-acceptable-range props 1))))))

(deftest test-before-page-change

  (testing "Updates state current-page"
    (let [current-page 2
          new-page 3
          state (-> {} ((reducers/set-current-page current-page)) atom)
          props {:state state}]
      (sut/before-page-change props 3)
      (is (= new-page (queries/current-page @state)))))

  (testing "Set's loading"
    (let [state (atom {})
          props {:state state}]
      (sut/before-page-change props 3)
      (is (true? (queries/is-loading? @state))))))

(deftest test-on-page-change

  (testing "Does not do anything if page is outside acceptable range"
    (with-redefs [sut/page-in-acceptable-range (constantly false)]
      (is (= ::sut/page-outside-acceptable-range (sut/on-page-change {} 1))))))
