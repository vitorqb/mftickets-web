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

(deftest test-active-project

  (testing "When no project and no active, nil"
    (is (nil? (sut/active-project {}))))

  (testing "When project id no longer exists, nil"
    (let [project-id 99
          state (-> {}
                    ((reducers/set-active-project-id project-id))
                    ((reducers/set-app-metadata-response {:success true
                                                          :body {:projects []}})))]
      (is (nil? (sut/active-project state)))))

  (testing "When project id and it exists"
    (let [project-id 88
          project {:id project-id}
          state (-> {}
                    ((reducers/set-active-project-id project-id))
                    ((reducers/set-app-metadata-response {:success true
                                                          :body {:projects [project]}})))]
      (is (= project (sut/active-project state)))))

  (testing "When projects but no selected id, pick first."
    (let [projects [{:id 99}]
          state (-> {} ((reducers/set-app-metadata-response {:success true
                                                             :body {:projects projects}})))]
      (is (= (first projects) (sut/active-project state))))))
