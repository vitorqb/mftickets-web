(ns mftickets-web.components.edit-project-page.reducers-test
  (:require [mftickets-web.components.edit-project-page.reducers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.edit-project-page.queries :as queries]))

(deftest test-new-picked-project

  (testing "Don't do anything if the new picked project is equal"
    (let [picked-project {:id 1 :name "Foo"}
          edited-project {:id 1 :name "Bar"}
          state (-> {}
                    ((sut/set-picked-project picked-project))
                    ((sut/set-edited-project edited-project))
                    ((sut/set-edit-project-response {:success true})))
          reducer (sut/new-picked-project picked-project)]
      (is (= state (reducer state)))))

  (testing "When the new picked project is not equal: "
    (let [picked-project {:id 1 :name "Foo"}
          edited-project {:id 1 :name "Bar"}
          new-picked-project {:id 2 :name "Baz"}
          state (-> {}
                    ((sut/set-picked-project picked-project))
                    ((sut/set-edited-project edited-project))
                    ((sut/set-edit-project-response {:success true})))
          reducer (sut/new-picked-project new-picked-project)
          new-state (reducer state)]

      (testing "sets picked project"
        (is (= new-picked-project (queries/picked-project new-state))))

      (testing "Sets edited project to nil"
        (is (nil? (:edited-project new-state))))

      (testing "Resets the edit response"
        (is (nil? (queries/edit-project-response new-state)))))))

(deftest test-edit-project-response

  (testing "Success response: "
    
    (let [state {}
          project {:id 1 :name "Foo"}
          response {:success true :body project}
          reducer (sut/edit-project-response response)
          new-state (reducer state)]

      (testing "Associates the new response"
        (is (= response (queries/edit-project-response new-state))))

      (testing "Updates the picked and edited projects"
        (is (= project (queries/edited-project new-state)))
        (is (= project (queries/picked-project new-state))))))

  (testing "Failed response: "
    (let [edited-project {:id 1 :name "Foo"}
          picked-project {:id 1 :name "Bar"}
          state (-> {}
                    ((sut/set-edited-project edited-project))
                    ((sut/set-picked-project picked-project)))
          response {:success false :body {::foo ::bar}}
          reducer (sut/edit-project-response response)
          new-state (reducer state)]

      (testing "Only set's response"
        (is (= response (queries/edit-project-response new-state)))
        (is (= edited-project (queries/edited-project new-state)))
        (is (= picked-project (queries/picked-project new-state)))))))
