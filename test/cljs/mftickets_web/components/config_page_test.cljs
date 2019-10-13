(ns mftickets-web.components.config-page-test
  (:require [mftickets-web.components.config-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-active-project-picker
  (testing "Passes project"
    (let [projects [{:id 1}]
          props {:config-page/projects projects}]
      (is (= projects
             (-> props sut/active-project-picker second :project-picker/projects))))))
