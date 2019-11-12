(ns mftickets-web.domain.select-test
  (:require [mftickets-web.domain.select :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-boolean->option
  (is (= {:value true :label "True"} (sut/boolean->option true)))
  (is (= {:value false :label "False"} (sut/boolean->option false))))
