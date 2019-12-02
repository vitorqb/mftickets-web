(ns mftickets-web.components.login-page.handlers-test
  (:require [mftickets-web.components.login-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.login-page.reducers :as reducers]))

(deftest test-on-email-input-change
  (let [state (atom {})
        props {:state state}
        new-value "Foo"
        e-new-state (-> @state ((reducers/set-email-value new-value)))]
    (is (= e-new-state (sut/on-email-input-change props new-value)))
    (is (= e-new-state @state))))

(deftest test-on-key-input-change
  (let [state (atom {})
        props {:state state}
        new-value "Foo"
        e-new-state (-> @state ((reducers/set-key-value new-value)))]
    (is (= e-new-state (sut/on-key-input-change props new-value)))
    (is (= e-new-state @state))))
