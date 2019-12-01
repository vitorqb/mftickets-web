(ns mftickets-web.components.template-picker.handlers-test
  (:require [mftickets-web.components.template-picker.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-on-select-change

  (testing "Propagates to on-template-picked"
    (let [picked-template {:id 99 :name "Foo"}
          picked-option {:label "Foo" :value picked-template}
          value-change (fn [x] [::value-change x])
          props {:template-picker.messages/on-template-picked value-change}]
      (is (= [::value-change picked-template]
             (sut/on-select-change props picked-option))))))
