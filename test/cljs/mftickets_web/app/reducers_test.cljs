(ns mftickets-web.app.reducers-test
  (:require [mftickets-web.app.reducers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.instances.router-dialog :as instances.router-dialog]
            [mftickets-web.components.router-dialog :as components.router-dialog]
            [mftickets-web.components.dialog.reducers :as components.dialog.reducers]))

(deftest test-m-display-router-dialog
  (let [reducer (sut/display-router-dialog)]
    (is (= {::instances.router-dialog/state
            {::components.router-dialog/dialog
             {:disabled? false}}}
           (reducer {})))))
