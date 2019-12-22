(ns mftickets-web.domain.template-test
  (:require [mftickets-web.domain.template :as sut]
            [cljs.test :refer-macros [is are deftest testing use-fixtures async]]
            [mftickets-web.domain.kw :as domain.kw]))

(deftest test-set-value-types-to-keyword
  (let [template {:sections [{:properties [{:value-type ::foo}]}]}]
    (is (= template
           (sut/set-value-types-to-keyword
            (update-in template [:sections 0 :properties 0 :value-type] domain.kw/kw->str))))))
