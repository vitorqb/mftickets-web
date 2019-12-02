(ns mftickets-web.components.view-project-page.handlers-test
  (:require [mftickets-web.components.view-project-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.view-project-page.queries :as queries]
            [mftickets-web.components.view-project-page.reducers :as reducers]))

(deftest test-on-delete-picked-project-prompt
  (is (= "Are you sure you want to delete: My Project?"
         (sut/on-delete-picked-project-prompt {:name "My Project"}))))

(deftest test-on-picked-project-change

  (testing "Reduces state with picked project"
    (let [state (atom {})
          picked-project {:id 1}]
      (sut/on-picked-project-change {:state state} picked-project)
      (is (= picked-project (queries/picked-project @state))))))

(deftest test-after-perform-delete-picked-project

  (testing "Reduces with after-delete"
    (with-redefs [reducers/after-delete-picked-project (fn [x] (fn [_] [::foo x]))]
      (let [response {:success true}
            state (atom {})
            props {:view-project-page.messages/refresh-app-metadata (constantly nil)
                   :state state}]
        (sut/after-perform-delete-picked-project props response)
        (is (= @state [::foo response])))))

  (testing "Propagates to app metadata"
    (let [refresh-app-metadata (constantly ::foo)
          events {:refresh-app-metadata refresh-app-metadata}
          props {:events events
                 :view-project-page.messages/refresh-app-metadata refresh-app-metadata
                 :state (atom {})}]
      (is (= ::foo (sut/after-perform-delete-picked-project props {:success true}))))))

(deftest test-on-delete-picked-project

  (let [with-confirmation (fn [x] [::with-confirmation x])
        picked-project {:id 1 :name "foo"}
        state (-> {} ((reducers/set-picked-project picked-project)) atom)
        props {:state state :view-project-page.messages/with-confirmation with-confirmation}
        result (sut/on-delete-picked-project props)]

    (testing "Calls with-confirmation"
      (is (= ::with-confirmation (first result))))

    (testing "Passes props to with-confirmation"
      (is (= props (-> result second :props))))

    (testing "Passes message to with-confirmation"
      (is (ifn? (-> result second :message))))

    (testing "Passes prompt"
      (is (= (sut/on-delete-picked-project-prompt picked-project)
             (-> result second :prompt))))))
