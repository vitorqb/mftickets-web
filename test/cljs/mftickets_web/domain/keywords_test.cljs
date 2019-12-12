(ns mftickets-web.domain.keywords-test
  (:require [mftickets-web.domain.keywords :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-select-ns
  (is (= {} (sut/select-ns {} 'foo.bar)))
  (is (= {:foo/bar 1} (sut/select-ns {:foo/bar 1} 'foo)))
  (is (= {:foo/bar 1} (sut/select-ns {:foo/bar 1 :bar/baz 2 :foo 3} 'foo))))
