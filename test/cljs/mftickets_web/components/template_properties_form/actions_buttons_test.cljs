(ns mftickets-web.components.template-properties-form.actions-buttons-test
  (:require [mftickets-web.components.template-properties-form.actions-buttons :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.button :as components.button]))

(deftest test-move-back-button
  (let [on-move-property-back #(do ::on-move-property-back)
        props {:template-properties-form.actions-buttons.messages/on-move-property-back
               on-move-property-back}
        [r-component r-props] (sut/move-back-button props)]

    (testing "Renders button"
      (is (= components.button/button r-component)))

    (testing "Passes label"
      (is (= "/\\" (:button/label r-props))))

    (testing "Passes on-click"
      (is (= on-move-property-back (:button.messages/on-click r-props))))))

(deftest test-move-forward-button
  (let [on-move-property-forward #(do ::on-move-property-forward)
        props {:template-properties-form.actions-buttons.messages/on-move-property-forward
               on-move-property-forward}
        [r-component r-props] (sut/move-forward-button props)]

    (testing "Renders button"
      (is (= components.button/button r-component)))

    (testing "Passes label"
      (is (= "\\/" (:button/label r-props))))

    (testing "Passes on-click"
      (is (= on-move-property-forward (:button.messages/on-click r-props))))))

(deftest test-remove-button
  (let [on-remove-property (constantly ::remove-property)
        props {:template-properties-form.actions-buttons.messages/on-remove-property
               on-remove-property}
        [r-component r-props] (sut/remove-button props)]

    (testing "Renders button"
      (is (= components.button/button r-component)))

    (testing "Passes label"
      (is (= sut/label (:button/label r-props))))

    (testing "Passes style"
      (is (= sut/style (:button/style r-props))))

    (testing "Passes callback"
      (is (= on-remove-property (:button.messages/on-click r-props))))))
