(ns mftickets-web.http-test
  (:require [mftickets-web.http :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-http-getter

  (testing "Not found"
    (let [fns {:a 1}
          getter (sut/http-getter fns (atom {}))]
      (is (nil? (:b getter)))
      (is (= (:b getter ::c) ::c))))

  (testing "Found"
    (let [foo-fn identity
          fns {:foo foo-fn}
          app-state (atom {:token ::token})
          getter (sut/http-getter fns app-state)]
      (is (= (:foo getter)
             {:token ::token})))))
