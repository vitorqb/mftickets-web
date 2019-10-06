(ns mftickets-web.components.create-project-page.handlers-test
  (:require [mftickets-web.components.create-project-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.create-project-page.reducers :as reducers]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.create-project-page.queries :as queries]))

(deftest test-on-create-project-submit--before

  (testing "Reduces state: "
    (let [state (-> {}
                    ((reducers/set-create-project-response {::foo ::bar}))
                    ((reducers/set-loading? false)))
          event (sut/on-create-project-submit--before)
          reducer (events.protocols/reduce! event)
          new-state (reducer state)]

      (testing "Set's loading"
        (is (true? (queries/loading? new-state))))

      (testing "Set's response to nil"
        (is (nil? (queries/create-project-response new-state)))))))

(deftest test-on-create-project-submit--after

  (testing "Reducers state: "
    (let [response {:success true}
          state (-> {} ((reducers/set-loading? true)))
          props {:events {:refresh-app-metadata-> (constantly nil)}}
          event (sut/on-create-project-submit--after props response)
          reducer (events.protocols/reduce! event)
          new-state (reducer state)]

      (testing "Set's loading"
        (is (false? (queries/loading? new-state)))))))
