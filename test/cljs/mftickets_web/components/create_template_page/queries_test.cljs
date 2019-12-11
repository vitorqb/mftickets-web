(ns mftickets-web.components.create-template-page.queries-test
  (:require [mftickets-web.components.create-template-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.domain.template :as domain.template]
            [mftickets-web.components.create-template-page.reducers :as reducers]
            [mftickets-web.user-messages :as user-messages]))

(deftest test-or-empty-template

  (let [project-id 999
        props {:create-template-page/current-project {:id project-id}}]

    (testing "Args unchanged if not nil"
      (let [arg {:id 1}]
        (is (= arg (sut/or-empty-template arg props)))))

    (testing "Empty template if arg is nil"
      (with-redefs [domain.template/gen-empty-template (fn [x] [::empty-template x])]
        (is (= [::empty-template {:project-id project-id}]
               (sut/or-empty-template nil props)))))))

(deftest test-edited-template

  (let [current-project-id 1
        props {:create-template-page/current-project {:id current-project-id}}]

    (testing "Defaults to new template if nil"
      (is (= (domain.template/gen-empty-template {:project-id current-project-id})
             (sut/edited-template {} props))))

    (testing "Simply get's if already on the state"
      (let [edited-template {:name "FOO" :project-id current-project-id}
            state (-> {} ((reducers/set-edited-template edited-template)))]
        (is (= edited-template (sut/edited-template state props)))))

    (testing "Always use the project-id from props!"
      (let [edited-template {:project-id 2}
            state (-> {} ((reducers/set-edited-template edited-template)))]
        (is (= {:project-id current-project-id}
               (sut/edited-template state props)))))))

(deftest test-is-loading?
  (is (true? (-> {} ((reducers/set-is-loading? true)) sut/is-loading?))))

(deftest test-create-template-response
  (let [response {::id 1}]
    (is (= response
           (-> {}
               ((reducers/set-create-template-response response))
               sut/create-template-response)))))

(deftest test-user-message

  (testing "Success"
    (let [response {:success true}
          state (-> {} ((reducers/set-create-template-response response)))]
      (is (= [:success user-messages/success]
             (sut/user-message state)))))

  (testing "Not Success"
    (let [response {:success false}
          state (-> {} ((reducers/set-create-template-response response)))]
      (is (= [:error user-messages/generic-error]
             (sut/user-message state)))))

  (testing "No response"
    (let [state (-> {} ((reducers/set-create-template-response nil)))]
      (is (nil? (sut/user-message state))))))
