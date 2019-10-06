(ns mftickets-web.components.project-picker-test
  (:require [mftickets-web.components.project-picker :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-project->select-option

  (testing "Base"
    (let [project {:name "foo"}]
      (is (= {:value project :label "foo"} (sut/project->select-option project)))))

  (testing "Nil if no project!"
    (is (nil? (sut/project->select-option nil)))))
