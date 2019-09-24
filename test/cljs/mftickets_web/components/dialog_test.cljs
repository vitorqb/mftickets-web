(ns mftickets-web.components.dialog-test
  (:require [mftickets-web.components.dialog :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.dialog.queries :as queries]
            [mftickets-web.components.dialog.reducers :as reducers]))

(deftest test-get-class

  (testing "Not disabled"
    (let [state (-> {} ((reducers/set-disabled? false)) atom)]
      (is (= [sut/base-class] (sut/get-class {:state state})))))

  (testing "Disabled"
    (let [state (-> {} ((reducers/set-disabled? true)) atom)]
      (is (= [sut/base-class sut/base-disabled-modifier]
             (sut/get-class {:state state}))))))


(deftest close-btn

  (testing "Set's disabled on click."
    (let [state (atom {})
          props {:state state}
          on-click (-> props sut/close-btn second :on-click)]
      (on-click {})
      (is (= ((reducers/set-disabled? true) {}) @state)))))
