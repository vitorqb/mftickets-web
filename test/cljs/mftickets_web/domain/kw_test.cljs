(ns mftickets-web.domain.kw-test
  (:require
   [mftickets-web.domain.kw :as sut]
   [cljs.test :refer-macros [is are deftest testing use-fixtures async]]))

(deftest test-kw->str
  (is (= ::foo (-> ::foo sut/kw->str sut/str->kw)))
  (is (= "mftickets-web.domain.kw-test/foo" (sut/kw->str ::foo))))
