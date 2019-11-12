(ns mftickets-web.domain.boolean-test
  (:require [mftickets-web.domain.boolean :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-string->boolean

  (is (nil? (sut/string->boolean nil)))
  (is (false? (sut/string->boolean "f")))
  (is (false? (sut/string->boolean "false")))
  (is (false? (sut/string->boolean "FALSE")))
  (is (true? (sut/string->boolean "true")))
  (is (true? (sut/string->boolean "t")))
  (is (true? (sut/string->boolean "TRUE")))
  (is (= [::sut/invalid-string "foo"] (sut/string->boolean "foo"))))

(deftest boolean->string
  (is (= "true" (sut/boolean->string true)))
  (is (= "false" (sut/boolean->string false))))

