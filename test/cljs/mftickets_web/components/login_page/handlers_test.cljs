(ns mftickets-web.components.login-page.handlers-test
  (:require [mftickets-web.components.login-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.login-page.reducers :as reducers]))

(deftest test-EmailChange
  (testing "Reduces setting email"
    (let [handler (sut/->EmailChange "FOO")
          reducer (events.protocols/reduce! handler)]
      (is (= (-> {} ((reducers/set-email-value "FOO"))) (reducer {}))))))
