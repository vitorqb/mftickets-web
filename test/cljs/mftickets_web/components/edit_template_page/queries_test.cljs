(ns mftickets-web.components.edit-template-page.queries-test
  (:require [mftickets-web.components.edit-template-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.edit-template-page.reducers :as reducers]
            [mftickets-web.user-messages :as user-messages]))

(deftest test-picked-template

  (let [picked-template {:id 1}
        state (-> {} ((reducers/set-picked-template picked-template)))]
    (is (= picked-template (sut/picked-template state)))))

(deftest test-edited-template

  (let [edited-template {:id 1}
        state (-> {} ((reducers/set-edited-template edited-template)))]
    (is (= edited-template (sut/edited-template state)))))

(deftest test-loading?
  (let [state (-> {} ((reducers/set-loading? false)) ((reducers/set-loading? true)))]
    (is (true? (sut/loading? state)))))

(deftest test-edited-template-submit-response
  (let [response {::foo 1}
        state (-> {} ((reducers/set-edited-template-submit-response response)))]
    (is (= response (sut/edited-template-submit-response state)))))

(deftest test-user-message

  (testing "No message if no response"
    (let [state (-> {} ((reducers/set-edited-template-submit-response nil)))]
      (is (nil? (sut/user-message state)))))

  (testing "Error message if not success"
    (let [response {:success false :body "FOO"}
          state (-> {} ((reducers/set-edited-template-submit-response response)))]
      (is (= {:message user-messages/generic-error
              :style :error}
             (sut/user-message state)))))

  (testing "Success message if success"
    (let [response {:success true :body "FOO"}
          state (-> {} ((reducers/set-edited-template-submit-response response)))]
      (is (= {:message user-messages/success
              :style :success}
             (sut/user-message state))))))
