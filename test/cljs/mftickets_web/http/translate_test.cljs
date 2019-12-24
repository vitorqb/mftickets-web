(ns mftickets-web.http.translate-test
  (:require [mftickets-web.http.translate :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-template->create-template
  (is (= {:id nil
          :name "FOO"
          :creation-date nil
          :sections [{:properties [{:value-type :bar}]}]}
         (sut/template->create-template
          {:id nil
           :name "FOO"
           :creation-date nil
           :sections [{:properties [{:value-type :bar}]}]
           ::tmp 1}))))

(deftest test-parse-bt-template
  (is (= {:id nil
          :name "FOO"
          :creation-date nil
          :sections [{:properties [{:value-type :bar/baz}]}]}
         (sut/parse-be-template
          {:id nil
           :name "FOO"
           :creation-date nil
           :sections [{:properties [{:value-type "bar/baz"}]}]}))))

(deftest test-parse-paged-be-templates

  (testing "Base"
    (let [response {:success
                    true
                    :body
                    {:page-number 1,
                     :page-size 50,
                     :total-items-count 1,
                     :items
                     [{:id 22,
                       :name "Pacs Bugs",
                       :project-id 2,
                       :creation-date "2019-12-21T14:10:08",
                       :sections
                       [{:id 11,
                         :name "Main",
                         :template-id 22,
                         :properties
                         [{:id 18,
                           :name "Solved?",
                           :template-section-id 11,
                           :is-multiple false,
                           :value-type
                           "domain.templates.properties/radio"}]}]}]}}]
      (is (= (update-in response [:body :items 0 :sections 0 :properties 0 :value-type] keyword)
             (sut/parse-paged-be-templates response)))))

  (testing "Failed response"
    (let [response {:success false :body {:error-msg "foo"}}]
      (is (= response (sut/parse-paged-be-templates response))))))
