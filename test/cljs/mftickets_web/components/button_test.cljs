(ns mftickets-web.components.button-test
  (:require [mftickets-web.components.button :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-get-button-css-class

  (testing "No modifiers"
    (is (= [sut/button-class] (sut/get-button-css-class {}))))

  (testing "Danger modifier"
    (is (= [sut/button-class sut/button-danger-class]
           (sut/get-button-css-class {:button/style :button/danger})))))

(deftest test-button
  (let [label "Label"
        on-click #(do ::on-click)
        props {:button/label label
               :button.messages/on-click on-click}
        [r-component r-props & r-children] (sut/button props)]

    (testing "Renders a div"
      (is (= :div r-component)))

    (testing "Passes base-class"
      (is (= sut/base-class (:class r-props))))

    (testing "Renders children..."
      (let [[r-component* r-props* & r-children*] (first r-children)]

        (testing "Single children"
          (is (= 1 (count r-children))))

        (testing "That is a div"
          (is (= :div r-component*)))

        (testing "Gives it button-class"
          (is (= [sut/button-class] (:class r-props*))))

        (testing "Calls on-click on click"
          (is (= ::on-click (-> r-props* :on-click (apply [])))))))))
