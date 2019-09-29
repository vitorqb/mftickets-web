(ns mftickets-web.components.projects-page.queries-test
  (:require [mftickets-web.components.projects-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.projects-page.reducers :as reducers]))

(deftest test-projects

  (testing "Nil"
    (is (nil? (sut/projects {}))))

  (testing "Nil (Failed Request)"
    (let [response {:success false :body [{:message "foo"}]}
          state (-> {} ((reducers/set-fetch-projects-response response)))]
      (is (nil? (sut/projects state)))))

  (testing "Not nil"
    (let [response {:success true :body [{:id 1}]}
          state (-> {} ((reducers/set-fetch-projects-response response)))]
      (is (= [{:id 1}] (sut/projects state))))))
