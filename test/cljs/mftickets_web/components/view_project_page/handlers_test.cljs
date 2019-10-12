(ns mftickets-web.components.view-project-page.handlers-test
  (:require [mftickets-web.components.view-project-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.view-project-page.queries :as queries]
            [mftickets-web.components.view-project-page.reducers :as reducers]))

(deftest test-on-delete-picked-project-prompt
  (is (= "Are you sure you want to delete: My Project?"
         (sut/on-delete-picked-project-prompt {:name "My Project"}))))

(deftest test-on-picked-project-change

  (testing "Reduces state with picked project"
    (let [state {}
          picked-project {:id 1}
          event (sut/on-picked-project-change picked-project)
          reducer (events.protocols/reduce! event)
          new-state (reducer state)]
      (is (= picked-project (queries/picked-project new-state))))))

(deftest test-on-delete-picked-project--perform--after

  (testing "Reduces with after-delete"
    (with-redefs [reducers/after-delete-picked-project (fn [x] [::foo x])]
      (let [response {:success true}
            events {:refresh-app-metadata (constantly nil)}
            props {:events events}
            event (sut/on-delete-picked-project--perform--after props response)]
        (is (= [::foo response] (events.protocols/reduce! event))))))

  (testing "Propagates to app metadata"
    (let [refresh-app-metadata (constantly ::foo)
          events {:refresh-app-metadata refresh-app-metadata}
          props {:events events}
          event (sut/on-delete-picked-project--perform--after props {})]
      (is (= [::foo] (events.protocols/propagate! event))))))

(deftest test-on-delete-picked-project

  (testing "Propagates to WithConfirmation"
    (let [picked-project {:name "Foo"}
          state (-> {} ((reducers/set-picked-project picked-project)) atom)
          WithConfirmation identity
          http {:delete-project (constantly nil)}
          props {:events {:WithConfirmation-> WithConfirmation} :state state :http http}
          event (sut/on-delete-picked-project props)
          [WithConfirmation-args & _ :as propagated] (events.protocols/propagate! event)]
      (is (= 1 (count propagated)))
      (is (= props (:props WithConfirmation-args)))
      (is (= (sut/on-delete-picked-project-prompt picked-project)
             (:prompt WithConfirmation-args))))))
