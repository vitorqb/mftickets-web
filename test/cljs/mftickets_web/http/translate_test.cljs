(ns mftickets-web.http.translate-test
  (:require [mftickets-web.http.translate :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-template->create-template
  (is (= {:id nil
          :name "FOO"
          :creation-date nil
          :sections [{:properties [{:value-type :bar}]}]}
         (sut/template->create-template
          {:id nil
           :name "FOO"
           :creation-date nil
           :sections [{:properties [{:value-type "bar"}]}]
           ::tmp 1}))))
