(ns mftickets-web.components.template-sections-form.handlers-test
  (:require [mftickets-web.components.template-sections-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [com.rpl.specter :as s]))

(deftest test-update-section
  (let [sections [{:id 1 :name "FOO"} {:id 2 :name "BAR"}]
        section {:id 1}
        update-fn #(assoc % :name "Hello World")]
    (is (= [{:id 1 :name "Hello World"} {:id 2 :name "BAR"}]
           (sut/update-section sections section update-fn)))))

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

  (let [section {:id 1 :name "foo"}
        sections [section]
        on-sections-change (fn [x] [::section-change x])
        props {:template-sections-form.messages/on-sections-change on-sections-change
               :template-sections-form.impl/section section
               :template-sections-form/sections sections}]
    (is (= [::section-change []]
           (sut/on-template-section-remove props)))))
