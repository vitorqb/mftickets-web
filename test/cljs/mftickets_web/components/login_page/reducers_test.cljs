(ns mftickets-web.components.login-page.reducers-test
  (:require [mftickets-web.components.login-page.reducers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.login-page.queries :as queries]))

(deftest before-email-submit

  (testing "Set's email submission state to loading"
    (let [state {}
          reducer (sut/before-email-submit)
          new-state (reducer state)]
      (is (= (queries/email-submission-current-state new-state)
             :ongoing))))

  (testing "Cleans old response"
    (let [state {:email-submission {:response {:status 200}}}
          reducer (sut/before-email-submit)
          new-state (reducer state)]
      (is (= {}
             (queries/email-submission-response new-state))))))

(deftest after-email-submit

  (testing "Set's state to :idle"
    (let [state {:email-submission {:current-state :ongoing}}
          response {:status 204}
          reducer (sut/after-email-submit response)
          new-state (reducer state)]
      (is (= :ongoing (queries/email-submission-current-state state)))
      (is (= :idle (queries/email-submission-current-state new-state)))))

  (testing "Stores response on state"
    (let [state {}
          response {::foo ::bar}
          reducer (sut/after-email-submit response)
          new-state (reducer state)]
      (is (= response (queries/email-submission-response new-state))))))

(deftest before-key-submit

  (testing "Set's state to ongoing"
    (let [state {}
          reducer (sut/before-key-submit)
          new-state (reducer state)]
      (is (= :ongoing
             (queries/key-submission-current-state new-state)))))

  (testing "Cleans old response"
    (let [response {:status 200}
          state {:key-submission {:response response}}
          reducer (sut/before-key-submit)
          new-state (reducer state)]
      (is (= response (queries/key-submission-response state)))
      (is (nil? (queries/key-submission-response new-state))))))


(deftest after-key-submit

  (testing "Set's state to idle"
    (let [state (-> {} ((sut/set-key-submission-state :ongoing)))
          reducer (sut/after-key-submit {})
          new-state (reducer state)]
      (is (= :ongoing (queries/key-submission-current-state state)))
      (is (= :idle (queries/key-submission-current-state new-state)))))

  (testing "Cleans old response"
    (let [response {:status 200}
          reducer (sut/after-key-submit response)
          state {}
          new-state (reducer state)]
      (is (= response (queries/key-submission-response new-state))))))
