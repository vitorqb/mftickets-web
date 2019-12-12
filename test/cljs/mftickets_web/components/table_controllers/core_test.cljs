(ns mftickets-web.components.table-controllers.core-test
  (:require [mftickets-web.components.table-controllers.core :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.table-controllers.page-updown :as c.table-controllers.page-updown]
            [com.rpl.specter :as s]
            [mftickets-web.components.table-controllers.page-size-selector :as c.table-controllers.page-size-selector]
            [clojure.set :as set]))

(deftest test-selected-shared-keys

  (testing "Shares message"
    (let [m {:table-controllers.messages/on-page-change 1}]
      (is (= m (sut/select-shared-keys m)))))

  (testing "Shares table-controllers"
    (let [m {:table-controllers/current-page 1}]
      (is (= m (sut/select-shared-keys m)))))

  (testing "Does not share state"
    (let [m {:state 1}]
      (is (= {} (sut/select-shared-keys m))))))

(deftest test-table-controller

  (testing "Renders a page-updown"
    (let [props {:table-controllers/current-page 1
                 :table-controllers/page-count 2}]
      (is (some #(= % [c.table-controllers.page-updown/page-updown props])
                (tree-seq vector? identity (sut/table-controller props))))))

  (testing "Renders a page-size-selector"
    (let [props
          {:table-controllers/current-page-size 10
           :table-controllers/page-count 5}

          result
          (sut/table-controller props)

          selector #(and (vector? %)
                         (= (first %) c.table-controllers.page-size-selector/page-size-selector))

          [r-component r-props]
          (s/select-first [s/ALL selector] (tree-seq vector? identity result))]

      (testing "Renders it"
        (is (= c.table-controllers.page-size-selector/page-size-selector r-component)))

      (testing "Passes props to it"
        (is (set/subset? (set props) (set r-props))))

      (testing "Passes state to it"
        (is (satisfies? ISwap (:state r-props)))))))
