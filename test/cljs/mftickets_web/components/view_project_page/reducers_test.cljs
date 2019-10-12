(ns mftickets-web.components.view-project-page.reducers-test
  (:require [mftickets-web.components.view-project-page.reducers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.view-project-page.queries :as queries]))

(deftest test-after-delete-picked-project

  (let [picked-project {:id 1}
        state (-> {} ((sut/set-loading? true)) ((sut/set-picked-project picked-project)))
        response {:success true}
        reducer (sut/after-delete-picked-project response)
        new-state (reducer state)]

    (testing "Set's loading to false"
      (is (false? (queries/loading? new-state))))

    (testing "Set's response"
      (is (= response (queries/delete-response new-state))))

    (testing "Cleans picked-project on success"
      (is (nil? (queries/picked-project new-state))))

    (testing "Dont't clean picked-project on failure"
      (let [response* (assoc response :success false)
            reducer* (sut/after-delete-picked-project response*)
            new-state* (reducer* state)]
        (is (= picked-project (queries/picked-project new-state*)))))))
