(ns mftickets-web.components.factories.loading-wrapper-test
  (:require [mftickets-web.components.factories.loading-wrapper :as sut :include-macros true]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-def-loading-wrapper

  (let [query-fn (fn [_] true)
        class "foo-class"]

    (testing "Query-fn returns nil -> fn returns nil"
      (let [query-fn* (fn [x] (:loading? x))
            loading-wrapper (sut/def-loading-wrapper foo1 query-fn* class)
            state (atom {:loading? false})
            props {:state state}]
        (is (nil? (loading-wrapper props)))))

    (testing "Query-fn returns true -> fn returns a div with class"
      (let [loading-wrapper (sut/def-loading-wrapper foo2 query-fn class)
            props {:state (atom nil)}
            [r-component r-props] (loading-wrapper props)]
        (is (= :div r-component))
        (is (= [class] (:class r-props)))))))
