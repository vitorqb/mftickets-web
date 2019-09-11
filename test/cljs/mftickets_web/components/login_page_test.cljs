(ns mftickets-web.components.login-page-test
  (:require [mftickets-web.components.login-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))


(deftest test-key-input

  (testing "Don't render if email not submited"
    (let [email-submit-response {:status 404}
          state {:email-submission-response email-submit-response}]
      (is (nil? (sut/key-input {:state state})))))

  (testing "Renders if email submited"
    (let [email-submit-response {:status 204}
          state {:email-submission-response email-submit-response}]
      (is (not (nil? (sut/key-input {:state state})))))))
