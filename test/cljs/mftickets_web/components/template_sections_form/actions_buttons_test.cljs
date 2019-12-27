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

(deftest test-move-section-back-button
  (let [on-move-section-back (constantly ::add-property)
        props {:template-sections-form.action-buttons.messages/on-move-section-back
               on-move-section-back}
        [r-component r-props] (sut/move-section-back-button props)]

    (testing "Renders button"
      (is (= components.button/button r-component)))

    (testing "Passes label"
      (is (= sut/move-section-back-btn-label (:button/label r-props))))

    (testing "Passes style"
      (is (= sut/move-section-back-btn-style (:button/style r-props))))

    (testing "Passes callback"
      (is (= on-move-section-back (:button.messages/on-click r-props))))))
