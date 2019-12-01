(ns mftickets-web.components.create-project-page.handlers-test
  (:require [mftickets-web.components.create-project-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.create-project-page.reducers :as reducers]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.create-project-page.queries :as queries]))

(deftest test-before-create-project-submit

  (testing "Reduces state: "
    (let [state (-> {}
                    ((reducers/set-create-project-response {::foo ::bar}))
                    ((reducers/set-loading? false))
                    atom)
          props {:state state}]

      (sut/before-create-project-submit props)

      (testing "Set's loading"
        (is (true? (queries/loading? @state))))

      (testing "Set's response to nil"
        (is (nil? (queries/create-project-response @state)))))))

(deftest test-after-create-project-submit

  (testing "Reducers state: "
    (let [response {:success true}
          state (-> {} ((reducers/set-loading? true)) atom)
          props {:create-project-page.messages/refresh-app-metadata (constantly nil)
                 :state state}]

      (sut/after-create-project-submit props response)

      (testing "Set's loading"
        (is (false? (queries/loading? @state)))))))
