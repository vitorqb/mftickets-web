(ns mftickets-web.components.view-project-page.queries-test
  (:require [mftickets-web.components.view-project-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.view-project-page.reducers :as reducers]))

(deftest test-picked-project
  (let [project {:id 1}
        state (-> {} ((reducers/set-picked-project project)))]
    (is (= project (sut/picked-project state)))))

(deftest test-delete-response
  (let [response {:status 200}
        state (-> {} ((reducers/set-delete-response response)))]
    (is (= response (sut/delete-response state)))))
