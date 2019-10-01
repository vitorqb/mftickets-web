(ns mftickets-web.app.queries-test
  (:require [mftickets-web.app.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.app.reducers :as reducers]))


(deftest test-get-app-metadata

  (testing "Failed response returns nil."
    (let [response {:success false :body {::foo ::bar}}
          state (-> {} ((reducers/set-app-metadata-response response)))]
      (is (= nil (sut/get-app-metadata state)))))

  (testing "Success request returns body."
    (let [response {:success true :body {::foo ::bar}}
          state (-> {} ((reducers/set-app-metadata-response response)))]
      (is (= {::foo ::bar} (sut/get-app-metadata state))))))

(deftest test-projects
  (let [projects [{:id 99}]
        state (-> {} ((reducers/set-app-metadata-response {:success true
                                                           :body {:projects projects}})))]
    (is (= projects (sut/projects state)))))
