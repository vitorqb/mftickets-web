(ns mftickets-web.components.template-form.sections-actions-buttons-test
  (:require [mftickets-web.components.template-form.sections-actions-buttons :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.button :as components.button]))

(deftest test-add-button
  (let [on-add-template-section #(do ::add-template-section)
        props {:template-form.sections-actions-buttons.messages/on-add-template-section
               on-add-template-section}
        [r-component r-props & _] (sut/add-button props)]

    (testing "Renders button"
      (is (= components.button/button r-component)))

    (testing "Gives it label"
      (is (= sut/add-section-label (:button/label r-props))))

    (testing "Gives it on-click"
      (is (= on-add-template-section (:button.messages/on-click r-props))))))

(deftest test-template-form-sections-actions-buttons
  (let [props {}
        [r-component r-props & r-children] (sut/template-form-sections-actions-buttons props)]

    (testing "Renders a wrapper div"
      (is (= :div r-component)))

    (testing "Gives it base-class"
      (is (= [sut/base-class] (:class r-props))))

    (testing "Renders first child..."
      (let [[r-component* r-props* & r-children*] (first r-children)]

        (testing "Is add-button"
          (is (= sut/add-button r-component*)))

        (testing "With props"
          (is (= props r-props*)))))))
