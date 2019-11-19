(ns mftickets-web.components.input.handlers-test
  (:require [mftickets-web.components.input.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-on-key-up

  (let [event (clj->js {:key "KEY"})]

    (testing "With handler defined"
      (let [on-key-up (fn [x] [::on-key-up x])
            props {:input.messages/on-key-up on-key-up}]
        (is (= [::on-key-up "KEY"] (sut/on-key-up props event)))))

    (testing "No handler"
      (is (nil? (sut/on-key-up {} event))))))

(deftest test-on-html-input-change

  (testing "Calls on-change with event value"
    (let [on-change (fn [x] [::on-change x])
          props {:input.messages/on-change on-change}
          event-value "FOO"
          event (clj->js {:target {:value "FOO"}})]
      (is (= [::on-change event-value]
             (sut/on-html-input-change props event))))))
