(ns mftickets-web.app.handlers-test
  (:require [mftickets-web.app.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.app.reducers :as reducers]))

(deftest test-CloseRouterDialog
  (let [exp-state (-> {} ((reducers/close-router-dialog)))
        reducer (events.protocols/reduce! (sut/->CloseRouterDialog))]
    (is (= exp-state (reducer {})))))
