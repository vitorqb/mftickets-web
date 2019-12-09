(ns mftickets-web.components.template-properties-form.actions-buttons-test
  (:require [mftickets-web.components.template-properties-form.actions-buttons :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.button :as components.button]))

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

