(ns mftickets-web.components.login-page-test
  (:require [mftickets-web.components.login-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.login-page.queries :as queries]
            [mftickets-web.components.login-page.reducers :as reducers]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.events :as events]))

(deftest email-input

  (testing "Set's state at change"
    (let [state (atom {})
          props {:state state}
          on-change-> (-> props sut/email-input (get-in [1 :events :on-change->]))]
      (events/react! {:state state} (on-change-> "vitorqb@gmail.com"))
      (is (= {:value "vitorqb@gmail.com"}
             (queries/email-input-state @state)))))

  (testing "Passes value"
    (let [state (atom {:inputs {:email {:value "Foo"}}})
          props {:state state}
          input-value (-> props sut/email-input second :value)]
      (is (= "Foo" input-value))))

  (testing "Passes disabled if email submit response is success."
    (let [email-submit-response {:status 204}
          state (atom {:email-submission {:response email-submit-response}})
          props {:state state}
          input-disabled? (-> props sut/email-input second :disabled)]
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

  (testing "Passes email value"
    (let [state (-> {}
                    ((reducers/set-key-value "abc"))
                    ((reducers/after-email-submit {:status 204}))
                    atom)
          props {:state state}
          key-value (-> props sut/key-input second :value)]
      (is (= "abc" key-value))))

  (testing "Sets email value at change"
    (let [state (-> {} ((reducers/after-email-submit {:status 204})) atom)
          props {:state state}
          on-change (-> props sut/key-input second :on-change)
          new-state (on-change "FOO")]
      (is (= {:value "FOO"}
             (queries/key-input-state new-state))))))

(deftest test-get-form-submit-handler

  (testing "Returns an key-submit handler if email submited successfully."
    (let [state (-> {} ((reducers/after-email-submit {:status 204})) atom)
          handler (sut/get-form-submit-handler {:state state})
          is-key-submit-handler? (-> handler meta ::sut/key-submit)]
      (is (true? (queries/email-has-been-submited-sucessfully? @state)))
      (is (true? is-key-submit-handler?))))

  (testing "Returns an email-submit handler if email not submited successfully."
    (let [state (-> {} ((reducers/after-email-submit {:status 500})) atom)
          handler (sut/get-form-submit-handler {:state state})
          is-email-submit-handler? (-> handler meta ::sut/email-submit)]
      (is (true? is-email-submit-handler?)))))

(deftest test-form

  (testing "Is loading if state is ongoing"
    (let [state (atom {:email-submission {:current-state :ongoing}})
          rendered (sut/form {:state state})]
      (is (true? (-> rendered second :is-loading?))))))
