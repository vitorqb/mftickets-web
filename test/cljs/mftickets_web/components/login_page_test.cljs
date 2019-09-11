(ns mftickets-web.components.login-page-test
  (:require [mftickets-web.components.login-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.login-page.queries :as queries]))

(deftest email-input

  (testing "Set's state at change"
    (let [state {}
          reduce! (fn [f] (f state))
          props {:state state :reduce! reduce!}
          on-change (-> props sut/email-input second :on-change)
          new-state (on-change "vitorqb@gmail.com")]
      (is (= {:value "vitorqb@gmail.com"}
             (queries/email-input-state new-state)))))

  (testing "Passes value"
    (let [state {:inputs {:email {:value "Foo"}}}
          props {:state state}
          input-value (-> props sut/email-input second :value)]
      (is (= "Foo" input-value)))))

(deftest test-key-input

  (testing "Don't render if email not submited"
    (let [email-submit-response {:status 404}
          state {:email-submission {:response email-submit-response}}]
      (is (nil? (sut/key-input {:state state})))))

  (testing "Renders if email submited"
    (let [email-submit-response {:status 204}
          state {:email-submission {:response email-submit-response}}]
      (is (not (nil? (sut/key-input {:state state})))))))


(deftest test-form

  (testing "Is loading if state is ongoing"
    (let [state {:email-submission {:current-state :ongoing}}
          rendered (sut/form {:state state})]
      (is (true? (-> rendered second :is-loading?))))))
