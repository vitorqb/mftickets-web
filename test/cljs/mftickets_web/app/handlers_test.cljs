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

(deftest test-update-current-project
  (let [project {:id 77}
        app-state (atom {})
        inject {:app-state app-state}
        exp-state (-> @app-state ((reducers/set-active-project-id (:id project))))]
    (sut/update-current-project inject project)
    (is (= exp-state @app-state))))
