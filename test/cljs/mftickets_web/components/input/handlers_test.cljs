(ns mftickets-web.components.input.handlers-test
  (:require [mftickets-web.components.input.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-OnChange

  (testing "Propagates change"
    (let [OnChange #(if (= % "FOO") %)
          props {:events {:OnChange-> OnChange}}
          event (clj->js {:target {:value "FOO"}})
          handler (sut/->OnChange props event)]
      (is (= ["FOO"] (events.protocols/propagate! handler)))))

  (testing "Don't propagate if no up handler"
    (let [event (clj->js {:target {:value "FOO"}})
          handler (sut/->OnChange {} event)]
      (is (= nil (events.protocols/propagate! handler))))))

(deftest test-OnKeyUp

  (testing "Propagates change"
    (let [OnKeyUp #(if (= % "KEY") "KEY")
          props {:events {:OnKeyUp-> OnKeyUp}}
          event (clj->js {:key "KEY"})
          handler (sut/->OnKeyUp props event)]
      (is (= ["KEY"] (events.protocols/propagate! handler)))))

  (testing "Don't propagate if not up handler"
    (let [event (clj->js {:key "KEY"})
          handler (sut/->OnKeyUp {} event)]
      (is (= nil (events.protocols/propagate! handler))))))

(deftest test-on-html-input-change

  (testing "Calls on-change with event value"
    (let [on-change (fn [x] [::on-change x])
          props {:input.messages/on-change on-change}
          event-value "FOO"
          event (clj->js {:target {:value "FOO"}})]
      (is (= [::on-change event-value]
             (sut/on-html-input-change props event))))))
