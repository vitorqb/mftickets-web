(ns mftickets-web.components.input.handlers-test
  (:require [mftickets-web.components.input.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-on-change

  (testing "Propagates change"
    (let [on-change #(if (= % "FOO") %)
          props {:events {:on-change-> on-change}}
          event (clj->js {:target {:value "FOO"}})
          handler (sut/on-change props event)]
      (is (= ["FOO"] (events.protocols/propagate! handler)))))

  (testing "Don't propagate if no up handler"
    (let [event (clj->js {:target {:value "FOO"}})
          handler (sut/on-change {} event)]
      (is (= nil (events.protocols/propagate! handler))))))

(deftest test-on-key-up

  (testing "Propagates change"
    (let [on-key-up #(if (= % "KEY") "KEY")
          props {:events {:on-key-up-> on-key-up}}
          event (clj->js {:key "KEY"})
          handler (sut/on-key-up props event)]
      (is (= ["KEY"] (events.protocols/propagate! handler)))))

  (testing "Don't propagate if not up handler"
    (let [event (clj->js {:key "KEY"})
          handler (sut/on-key-up {} event)]
      (is (= nil (events.protocols/propagate! handler))))))
