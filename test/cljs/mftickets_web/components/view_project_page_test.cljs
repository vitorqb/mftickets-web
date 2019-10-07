(ns mftickets-web.components.view-project-page-test
  (:require [mftickets-web.components.view-project-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.view-project-page.reducers :as reducers]
            [mftickets-web.components.project-form :as components.project-form]))


(deftest test-project-display-form

  (testing "Renders nil if no picked project"
    (let [state (-> {} ((reducers/set-picked-project nil)) atom)
          props {:state state}
          project-display-form (sut/project-display-form props)]
      (is (nil? project-display-form))))

  (testing "Renders a project-form with project and inputs metadata."
    (let [project {:id 99}
          state (-> {} ((reducers/set-picked-project project)) atom)
          props {:state state}
          project-display-form (sut/project-display-form props)]
      (is (= components.project-form/project-form (first project-display-form)))
      (is (= project (-> project-display-form second :project-form/edited-project)))
      (is (= (sut/project-display-form-inputs-metadata)
             (-> project-display-form second :project-form/inputs-metadata))))))
