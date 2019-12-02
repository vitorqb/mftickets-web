(ns mftickets-web.components.login-page-test
  (:require [mftickets-web.components.login-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.login-page.queries :as queries]
            [mftickets-web.components.login-page.reducers :as reducers]
            [mftickets-web.components.login-page.handlers :as handlers]))

(deftest email-input

  (testing "Set's state at change"
    (let [state (atom {})
          props {:state state}
          on-change (-> props sut/email-input (get-in [1 :input.messages/on-change]))]
      (on-change "vitorqb@gmail.com")
      (is (= {:value "vitorqb@gmail.com"}
             (queries/email-input-state @state)))))

  (testing "Passes value"
    (let [state (atom {:inputs {:email {:value "Foo"}}})
          props {:state state}
          input-value (-> props sut/email-input second :input/value)]
      (is (= "Foo" input-value))))

  (testing "Passes disabled if email submit response is success."
    (let [email-submit-response {:status 204}
          state (atom {:email-submission {:response email-submit-response}})
          props {:state state}
          input-disabled? (-> props sut/email-input second :input/disabled)]
      (is (true? (queries/email-has-been-submited-sucessfully? @state)))
      (is (true? input-disabled?)))))

(deftest test-key-input

  (testing "Don't render if email not submited"
    (let [email-submit-response {:status 404}
          state (atom {:email-submission {:response email-submit-response}})]
      (is (nil? (sut/key-input {:state state})))))

  (testing "Renders if email submited"
    (let [email-submit-response {:status 204}
          state (atom {:email-submission {:response email-submit-response}})]
      (is (not (nil? (sut/key-input {:state state}))))))

  (testing "Passes key value"
    (let [state (-> {}
                    ((reducers/set-key-value "abc"))
                    ((reducers/after-email-submit {:status 204}))
                    atom)
          props {:state state}
          key-value (-> props sut/key-input second :input/value)]
      (is (= "abc" key-value))))

  (testing "Sets email value at change"
    (let [state (-> {} ((reducers/after-email-submit {:status 204})) atom)
          props {:state state}
          on-change (-> props sut/key-input second :input.messages/on-change)]
      (on-change "FOO")
      (is (= {:value "FOO"}
             (queries/key-input-state @state))))))

(deftest test-get-form-submit-handler

  (testing "Returns an key-submit handler if email submited successfully."
    (with-redefs [handlers/on-key-submit (fn [x] [::on-key-submit x])]
      (let [state (-> {} ((reducers/after-email-submit {:status 204})) atom)
            props {:state state}
            handler (sut/get-form-submit-handler props)]
        (is (true? (queries/email-has-been-submited-sucessfully? @state)))
        (is (= [::on-key-submit props] handler)))))

  (testing "Returns an email-submit handler if email submited successfully."
    (with-redefs [handlers/on-email-submit (fn [x] [::on-email-submit x])]
      (let [state (-> {} ((reducers/after-email-submit {:status 500})) atom)
            props {:state state}
            handler (sut/get-form-submit-handler props)]
        (is (false? (queries/email-has-been-submited-sucessfully? @state)))
        (is (= [::on-email-submit props] handler))))))

(deftest test-form

  (testing "Is loading if state is ongoing"
    (let [state (atom {:email-submission {:current-state :ongoing}})
          rendered (sut/form {:state state})]
      (is (true? (-> rendered second :is-loading?))))))
