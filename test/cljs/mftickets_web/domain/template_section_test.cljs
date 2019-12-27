(ns mftickets-web.domain.template-section-test
  (:require [mftickets-web.domain.template-section :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-gen-empty-template-section
  (let [temp-id 1232141
        template-id 999]
    (with-redefs [sut/gen-temp-id (constantly temp-id)]
      (is (= {:id nil
              :name ""
              :template-id template-id
              :properties []
              ::sut/is-new? true
              ::sut/temp-id temp-id}
             (sut/gen-empty-template-section {:template-id template-id}))))))

(deftest test-update-section-in-coll

  (testing "Base"
    (let [sections [{:id 1 :name "FOO"} {:id 2 :name "BAR"}]
          section {:id 1}
          update-fn #(assoc % :name "Hello World")]
      (is (= [{:id 1 :name "Hello World"} {:id 2 :name "BAR"}]
             (sut/update-section-in-coll sections section update-fn)))))

  (testing "Using recently created sections"
    (let [section1 {:id 1 :name "FOO"}
          section2 {::sut/temp-id 1
                    ::sut/is-new? true
                    :name ""}
          section3 {::sut/temp-id 2
                    ::sut/is-new? true
                    :name ""}
          sections [section1 section2 section3]
          update-fn #(assoc % :name "Foo")]
      (is (= [section1 section2 (update-fn section3)]
             (sut/update-section-in-coll sections section3 update-fn))))))

(deftest test-prepend-property-to-coll

  (testing "Section not found"
    (let [section {:id 1}
          sections [{:id 2}]]
      (is (= sections (sut/prepend-property-to-coll sections section {})))))

  (testing "Section is found"
    (let [property1 {:id :property1}
          property2 {:id :property2}
          new-property {:id :new-property}
          section {:id 1 :properties [property1 property2]}
          exp-section (assoc section :properties [new-property property1 property2])
          sections [{:id 2} section]
          exp-sections [{:id 2} exp-section]]
      (is (= exp-sections (sut/prepend-property-to-coll sections section new-property))))))

(deftest test-update-properties-order-from-coll

  (testing "Base"
    (let [property1 {:id :property1}
          property2 {:id :property2}
          section {:id :section :properties [property1 property2]}
          sections [{:id 1} section]

          exp-property1 (assoc property1 :order 0)
          exp-property2 (assoc property2 :order 1)
          exp-properties [exp-property1 exp-property2]
          exp-section (assoc section :properties exp-properties)
          exp-sections (assoc sections 1 exp-section)]

      (is (= exp-sections (sut/update-properties-order-from-coll sections section))))))
