(ns mftickets-web.components.create-template-page.handlers-test
  (:require [mftickets-web.components.create-template-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.create-template-page.reducers :as reducers]
            [mftickets-web.components.create-template-page.queries :as queries]))

(deftest test-on-new-template-change

  (let [template {:name "FOO"}
        new-template {:name "BAR"}
        initial-state (-> {} ((reducers/set-edited-template template)))
        exp-state (-> initial-state ((reducers/set-edited-template new-template)))
        state (atom initial-state)
        props {:state state}]
    (is (= exp-state (sut/on-new-template-change props new-template)))
    (is (= exp-state @state))))

(deftest before-new-template-submit

  (testing "Sets loading to true"
    (let [init-state (-> {} ((reducers/set-is-loading? false)))
          state (atom init-state)
          props {:state state}]
      (sut/before-new-template-submit props)
      (is (true? (queries/is-loading? @state)))))

  (testing "Cleans old request"
    (let [init-state (-> {} ((reducers/set-create-template-response {::id 1})))
          state (atom init-state)
          props {:state state}]
      (sut/before-new-template-submit props)
      (is (nil? (queries/create-template-response @state))))))

(deftest after-new-template-submit

  (testing "Sets loading to false"
    (let [init-state (-> {} ((reducers/set-is-loading? true)))
          state (atom init-state)
          props {:state state}]
      (sut/after-new-template-submit props {})
      (is (false? (queries/is-loading? @state)))))

  (testing "Saves request"
    (let [init-state (-> {} ((reducers/set-create-template-response {::id 1})))
          state (atom init-state)
          props {:state state}
          new-request {::id 2}]
      (sut/after-new-template-submit props new-request)
      (is (= new-request (queries/create-template-response @state))))))
