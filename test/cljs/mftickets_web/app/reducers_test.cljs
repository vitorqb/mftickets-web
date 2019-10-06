(ns mftickets-web.app.reducers-test
  (:require [mftickets-web.app.reducers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.instances.router-dialog :as instances.router-dialog]
            [mftickets-web.components.router-dialog :as components.router-dialog]
            [mftickets-web.components.dialog.reducers :as components.dialog.reducers]
            [mftickets-web.app.queries :as queries]))

(deftest test-m-display-router-dialog
  (let [reducer (sut/display-router-dialog)]
    (is (= {::instances.router-dialog/state
            {::components.router-dialog/dialog
             {:disabled? false}}}
           (reducer {})))))

(deftest test-set-app-metadata
  (let [app-metadata-response {:success true :body {:projects [{:id 2}]}}
        reducer (sut/set-app-metadata-response app-metadata-response)
        state {}
        new-state (reducer state)]
    (is (= (:body app-metadata-response) (queries/get-app-metadata new-state)))))
