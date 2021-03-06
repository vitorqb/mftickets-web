(ns mftickets-web.components.template-sections-form.handlers-test
  (:require [mftickets-web.components.template-sections-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [com.rpl.specter :as s]
            [mftickets-web.domain.template-section :as domain.template-section]
            [mftickets-web.domain.template-property :as domain.template-property]
            [mftickets-web.domain.sequences :as domain.sequences]))

(deftest test-on-template-section-input-change

  (let [on-sections-change (fn [x] [::section-change x])
        section {:id 1 :name "FOO"}
        other-section {:id 2 :name "BAZ"}
        new-name "BAR"
        props {:template-sections-form.messages/on-sections-change on-sections-change
               :template-sections-form/sections [section other-section]
               :template-sections-form.impl/section section}
        update-value-fn #(assoc %1 :name %2)
        metadata {:factories.input/update-value-fn update-value-fn}]

    (is (= [::section-change [(assoc section :name new-name) other-section]]
           (sut/on-template-section-input-change props metadata new-name)))))

(deftest test-on-template-section-remove

  (testing "Base"
    
    (let [section {:id 1 :name "foo"}
          sections [section]
          on-sections-change (fn [x] [::section-change x])
          props {:template-sections-form.messages/on-sections-change on-sections-change
                 :template-sections-form.impl/section section
                 :template-sections-form/sections sections}]
      (is (= [::section-change []]
             (sut/on-template-section-remove props)))))

  (testing "With recently created sections"
    (let [section1 (domain.template-section/gen-empty-template-section {:template-id 1})
          section2 (domain.template-section/gen-empty-template-section {:template-id 1})
          sections [section1 section2]
          on-sections-change identity
          props {:template-sections-form.messages/on-sections-change on-sections-change
                 :template-sections-form.impl/section section2
                 :template-sections-form/sections sections}]
      (is (= [section1] (sut/on-template-section-remove props))))))

(deftest test-on-template-section-move-back
  (let [on-sections-change identity
        sections [{:id 0} {:id 1} {:id 2}]
        props {:template-sections-form.messages/on-sections-change on-sections-change
               :template-sections-form.impl/section {:id 1}
               :template-sections-form/sections sections}]
    (is (= [{:id 1 :order 0} {:id 0 :order 1} {:id 2 :order 2}]
           (sut/on-template-section-move-back props)))))

(deftest test-on-template-section-move-forward
  (let [on-sections-change identity
        sections [{:id 0} {:id 1} {:id 2}]
        props {:template-sections-form.messages/on-sections-change on-sections-change
               :template-sections-form.impl/section {:id 1}
               :template-sections-form/sections sections}]
    (is (= [{:id 0 :order 0} {:id 2 :order 1} {:id 1 :order 2}]
           (sut/on-template-section-move-forward props)))))

(deftest test-on-add-template-property
  (let [on-sections-change (fn [x] [::sections-change x])
        property1 {:id 1 :order 0}
        property2 {:id 2 :order 1}
        property3 (domain.template-property/gen-empty-template-property {:template-section-id 3})
        properties [property1 property2]
        section1 {:id 3 :properties properties}
        new-properties [property3 property1 property2]
        new-section1 (assoc section1 :properties new-properties)
        section2 {:id 4 :properties []}
        sections [section1 section2]
        new-sections [new-section1 section2]
        exp-new-sections (domain.template-section/update-properties-order-from-coll
                          new-sections
                          new-section1)
        props {:template-sections-form.messages/on-sections-change on-sections-change
               :template-sections-form/sections sections
               :template-sections-form.impl/section section1}]
    (with-redefs (domain.template-property/gen-empty-template-property (constantly property3))
      (is (= [::sections-change exp-new-sections]
             (sut/on-add-template-property props))))))
