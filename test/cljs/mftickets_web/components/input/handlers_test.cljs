(ns mftickets-web.components.input.handlers-test
  (:require [mftickets-web.components.input.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-on-key-down

  (let [event (clj->js {:key "KEY"})]

    (testing "With handler defined"
      (let [on-key-down (fn [x] [::on-key-down x])
            props {:input.messages/on-key-down on-key-down}]
        (is (= [::on-key-down "KEY"] (sut/on-key-down props event)))))

    (testing "No handler"
      (is (nil? (sut/on-key-down {} event))))))

(deftest test-on-html-input-change

  (testing "Calls on-change with event value"
    (let [on-change (fn [x] [::on-change x])
          props {:input.messages/on-change on-change}
          event-value "FOO"
          event (clj->js {:target {:value "FOO"}})]
      (is (= [::on-change event-value]
             (sut/on-html-input-change props event))))))
