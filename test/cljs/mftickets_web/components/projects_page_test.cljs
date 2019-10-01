(ns mftickets-web.components.projects-page-test
  (:require [mftickets-web.components.projects-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.projects-page.reducers :as reducers]))

(deftest test-projects-table

  (let [projects [{:id 1}]
        state (-> {} ((reducers/set-fetch-projects-response {:success true :body projects})))
        props {:state (atom state)}
        component (sut/projects-table props)]
    (is (= {:table/config sut/projects-table-config
            :table/rows projects}
           (second component)))))

(deftest test-get-loading-wrapper-class
  (is (= [sut/loading-wrapper-class sut/loading-wrapper-inactive-modifier]
         (sut/get-loading-wrapper-class false)))
  (is (= [sut/loading-wrapper-class] (sut/get-loading-wrapper-class true))))
