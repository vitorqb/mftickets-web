(ns mftickets-web.components.header.handlers-test
  (:require [mftickets-web.components.header.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-display-router
  (let [call-count (atom 0)
        display-router-dialog #(swap! call-count inc)
        props {:messages {:display-router-dialog display-router-dialog}}
        event (sut/display-router props)]
    (is (nil? (events.protocols/run-effects! event)))
    (is (= 1 @call-count))))
