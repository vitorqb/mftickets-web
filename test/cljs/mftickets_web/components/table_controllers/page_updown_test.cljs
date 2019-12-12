(ns mftickets-web.components.table-controllers.page-updown-test
  (:require [mftickets-web.components.table-controllers.page-updown :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-get-current-page-label

  (let [props #:table-controllers{:current-page 1 :page-count 10}]
    (is (= "Page 1 of 10" (sut/get-current-page-label props)))))

(deftest test-btn+

  (testing "Sends on-page-change message"
    (let [on-page-change (fn [x] [::page-change x])
          current-page 2
          page-count 5
          props {:table-controllers/current-page current-page
                 :table-controllers/page-count page-count
                 :table-controllers.messages/on-page-change on-page-change}
          [_ r-props] (sut/btn+ props)]
      (is (= [::page-change (inc current-page)]
             (-> r-props :button.messages/on-click (apply [])))))))

(deftest test-btn-

  (testing "Sends on-page-change message"
    (let [on-page-change (fn [x] [::page-change x])
          current-page 2
          page-count 5
          props {:table-controllers/current-page current-page
                 :table-controllers/page-count page-count
                 :table-controllers.messages/on-page-change on-page-change}
          [_ r-props] (sut/btn- props)]
      (is (= [::page-change (dec current-page)]
             (-> r-props :button.messages/on-click (apply [])))))))

(deftest test-page-updown

  (testing "Renders a span with the current page"
    (let [props #:table-controllers{:current-page 2 :page-count 5}]
      (is (some #(= [:span (sut/get-current-page-label props)])
                (tree-seq vector? identity (sut/page-updown props)))))))
