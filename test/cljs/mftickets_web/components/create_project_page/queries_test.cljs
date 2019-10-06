(ns mftickets-web.components.create-project-page.queries-test
  (:require [mftickets-web.components.create-project-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.create-project-page.reducers :as reducers]
            [mftickets-web.user-messages :as user-messages]))


(deftest test-raw-project
  (let [raw-project {:name "FOO"}
        state (-> {} ((reducers/set-raw-project raw-project)))]
    (is (= raw-project (sut/raw-project state)))))

(deftest test-create-project-response
  (let [response {:success true}
        state (-> {} ((reducers/set-create-project-response response)))]
    (is (= response (sut/create-project-response state)))))

(deftest test-loading
  (let [state (-> {} ((reducers/set-loading? true)))]
    (is (true? (sut/loading? state)))))

(deftest test-user-message

  (testing "No message"
    (is (nil? (sut/user-message {}))))

  (testing "Error"
    (let [response {:success false}
          state (-> {} ((reducers/set-create-project-response response)))]
      (is (= [:error user-messages/generic-error]
             (sut/user-message state)))))

  (testing "Success"
    (let [response {:success true}
          state (-> {} ((reducers/set-create-project-response response)))]
      (is (= [:success user-messages/success]
             (sut/user-message state))))))
