(ns mftickets-web.components.view-template-page.handlers-test
  (:require [mftickets-web.components.view-template-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing use-fixtures async]]
            [mftickets-web.components.view-template-page.queries :as queries]
            [mftickets-web.components.view-template-page.reducers :as reducers]))

(deftest test-delete-template-confirmation-prompt

  (let [state (-> {} ((reducers/on-picked-template {:name "Foo"})) atom)]
    (is (= "Are you sure you want to delete 'Foo'?"
           (sut/delete-template-confirmation-prompt {:state state})))))

(deftest test-before-delete-template

  (testing "Sets loading"
    (let [state (atom {})
          props {:state state}]
      (is (false? (queries/is-loading? @state)))
      (sut/before-delete-template props)
      (is (true? (queries/is-loading? @state)))))

  (testing "Resets response"
    (let [old-response {:success false}
          state (atom (-> {} ((reducers/set-delete-template-response old-response))))
          props {:state state}]
      (is (= old-response (queries/delete-template-response @state)))
      (sut/before-delete-template props)
      (is (nil? (queries/delete-template-response @state))))))

(deftest test-after-delete-template

  (testing "Sets loading to false"
    (let [state (-> {} ((reducers/set-is-loading? true)) atom)
          props {:state state}]
      (is (true? (queries/is-loading? @state)))
      (sut/after-delete-template props {})
      (is (false? (queries/is-loading? @state)))))

  (testing "Sets response"
    (let [state (atom {})
          props {:state state}
          response {:success true}]
      (is (nil? (queries/delete-template-response @state)))
      (sut/after-delete-template props response)
      (is (= response (queries/delete-template-response @state))))))

(deftest test-on-delete-template

  (let [with-confirmation (fn [x] [::with-confirmation x])
        props {:state (atom {}) :view-template-page.messages/with-confirmation with-confirmation}
        result (sut/on-delete-template props)]

    (testing "Calls with-confirmation"
      (is (= (first result) ::with-confirmation)))

    (testing "Passes message"
      (is (ifn? (-> result second :message))))

    (testing "Passes prompt"
      (is (string? (-> result second :prompt))))))
