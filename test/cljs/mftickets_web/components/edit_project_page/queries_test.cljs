(ns mftickets-web.components.edit-project-page.queries-test
  (:require [mftickets-web.components.edit-project-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.edit-project-page.reducers :as reducers]
            [mftickets-web.user-messages :as user-messages]))

(deftest test-picked-project

  (testing "Base"
    (let [project {:id 99 :name "Foo"}
          state (-> {} ((reducers/set-picked-project project)))]
      (is (= project (sut/picked-project state))))))

(deftest test-edited-project

  (testing "Equals to picked project if not edited project set"
    (let [picked-project {:id 99 :name "Foo"}
          state (-> {} ((reducers/set-picked-project picked-project)))]
      (is (= picked-project (sut/edited-project state)))))

  (testing "Base"
    (let [project {:id 99 :name "Foo"}
          state (-> {} ((reducers/set-edited-project project)))]
      (is (= project (sut/edited-project state))))))

(deftest test-loading?

  (testing "Base"
    (let [state (-> {} ((reducers/set-loading? true)))]
      (is (= true (sut/loading? state))))))

(deftest test-edit-project-response

  (testing "Base"
    (let [response {:status 203}
          state (-> {} ((reducers/set-edit-project-response response)))]
      (is (= response (sut/edit-project-response state))))))

(deftest test-user-message

  (testing "Failed edit request"
    (let [edit-response {:success false}
          state (-> {} ((reducers/set-edit-project-response edit-response)))]
      (is (= [:error user-messages/generic-error]
             (sut/user-message state)))))

  (testing "Success edit request"
    (let [edit-response {:success true}
          state (-> {} ((reducers/set-edit-project-response edit-response)))]
      (is (= [:success user-messages/success]
             (sut/user-message state)))))

  (testing "No response"
    (is (= nil (sut/user-message {})))))
