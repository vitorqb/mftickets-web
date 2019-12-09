(ns mftickets-web.components.template-sections-form.actions-buttons-test
  (:require [mftickets-web.components.template-sections-form.actions-buttons :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.button :as components.button]))

(deftest test-add-property-button
  (let [on-add-property (constantly ::add-property)
        props {:template-sections-form.action-buttons.messages/on-add-property on-add-property}
        [r-component r-props] (sut/add-property-button props)]

    (testing "Renders button"
      (is (= components.button/button r-component)))

    (testing "Passes label"
      (is (= sut/add-property-btn-label (:button/label r-props))))

    (testing "Passes style"
      (is (= sut/add-property-btn-style (:button/style r-props))))

    (testing "Passes callback"
      (is (= on-add-property (:button.messages/on-click r-props))))))
