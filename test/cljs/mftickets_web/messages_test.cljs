(ns mftickets-web.messages-test
  (:require [mftickets-web.messages :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.instances.router-dialog :as instances.router-dialog]
            [mftickets-web.components.router-dialog :as components.router-dialog]))

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

(deftest test-m-display-router-dialog
  (let [reducer (sut/m-display-router-dialog)
        state {}
        new-state (reducer state)]
    (is (= {::instances.router-dialog/state
            {::components.router-dialog/dialog
             {:disabled? false}}}
           new-state))))
