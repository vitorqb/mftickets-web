(ns mftickets-web.components.login-page.queries-test
  (:require [mftickets-web.components.login-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-email-input-state

  (testing "Empty"
    (let [state {}]
      (is (nil? (sut/email-input-state state)))))

  (testing "Not empty"
    (let [input-state {:infractions [] :value "FOO"}
          state {:inputs {:email input-state}}
          queried (sut/email-input-state state)]
      (is (= input-state queried)))))

(deftest key-email-input-state

  (testing "Empty"
    (let [state {}]
      (is (nil? (sut/key-input-state state)))))

  (testing "Not empty"
    (let [input-state {:infractions [] :value "FOO"}
          state {:inputs {:key input-state}}
          queried (sut/key-input-state state)]
      (is (= input-state queried)))))

(deftest test-email-has-been-submited-sucessfully?

  (testing "No submission"
    (is (nil? (sut/email-has-been-submited-sucessfully? {}))))

  (testing "404 response"
    (let [response {:status 404}
          state {:email-submission {:response response}}
          queried (sut/email-has-been-submited-sucessfully? state)]
      (is (false? queried))))

  (testing "Correct response"
    (let [response {:status 204}
          state {:email-submission {:response response}}
          queried (sut/email-has-been-submited-sucessfully? state)]
      (is (true? queried)))))


(deftest test-email-submission-state

  (testing "Base"
    (let [current-state :ongoing
          state {:email-submission {:current-state current-state}}
          queried (sut/email-submission-current-state state)]
      (is (= queried :ongoing)))))
