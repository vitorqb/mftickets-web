(ns mftickets-web.components.templates-page.queries-test
  (:require [mftickets-web.components.templates-page.queries :as sut]
            [mftickets-web.components.templates-page.reducers :as reducers]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))


(deftest test-templates

  (testing "Null if failed request"
    (let [response {:success false :body [::foo]}
          state (-> {} ((reducers/set-templates-http-response response)) atom)]
      (is (nil? (sut/templates @state)))))

  (testing "Null if no response"
    (let [state (atom {})]
      (is (nil? (sut/templates @state)))))

  (testing "Not null"
    (let [response {:success true :body [::foo]}
          state (-> {} ((reducers/set-templates-http-response response)) atom)]
      (is (= [::foo] (sut/templates @state))))))
