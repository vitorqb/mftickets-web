(ns mftickets-web.messages-test
  (:require [mftickets-web.messages :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-messages-getter

  (testing "Returns nil if not found"
    (let [messages (sut/messages-getter {} ::app-state)]
      (is (nil? (:foo messages)))))

  (testing "Runs the reducer"
    (let [reducer (fn [x] #(assoc % ::foo x))
          app-state (atom {})
          messages (sut/messages-getter {:myfun reducer} app-state)]
      ((:myfun messages) ::bar)
      (is (= @app-state {::foo ::bar})))))

