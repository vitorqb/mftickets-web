(ns mftickets-web.app.handlers-test
  (:require [mftickets-web.app.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.app.reducers :as reducers]))

(deftest test-close-router-dialog
  (let [app-state (atom {})
        exp-state (-> @app-state ((reducers/close-router-dialog)))
        inject {:app-state app-state}]
    (is (= exp-state (sut/close-router-dialog inject)))))

(deftest test-UpdateCurrentProject
  (let [project {:id 77}
        exp-state (-> {} ((reducers/set-active-project-id (:id project))))
        reducer (events.protocols/reduce! (sut/->UpdateCurrentProject project))]
    (is (= exp-state (reducer {})))))
