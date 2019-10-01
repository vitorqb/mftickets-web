(ns mftickets-web.components.edit-project-page.handlers-test
  (:require [mftickets-web.components.edit-project-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.edit-project-page.reducers :as reducers]))

(deftest test-on-edited-project-submit--before
  (let [state {}
        event (sut/on-edited-project-submit--before)
        reducer (events.protocols/reduce! event)]
    (is (= (-> state
               ((reducers/set-loading? true))
               ((reducers/set-edit-project-response nil)))
           (reducer state)))))

(deftest test-on-edited-project-submit--after
  (let [state {}
        props {:events {:refresh-app-metadata-> (constantly nil)}}
        response {:status 204}
        event (sut/on-edited-project-submit--after props response)
        reducer (events.protocols/reduce! event)]
    (is (= (-> state
               ((reducers/set-loading? false))
               ((reducers/set-edit-project-response response)))
           (reducer state)))))
