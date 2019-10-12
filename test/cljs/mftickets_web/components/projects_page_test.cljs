(ns mftickets-web.components.projects-page-test
  (:require [mftickets-web.components.projects-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.projects-page.reducers :as reducers]))

(deftest test-projects-table

  (let [projects [{:id 1}]
        props {:state (atom {}) :projects-page/projects projects}
        component (sut/projects-table props)]
    (is (= {:table/config sut/projects-table-config
            :table/rows projects}
           (second component)))))
