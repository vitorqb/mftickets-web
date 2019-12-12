(ns mftickets-web.components.templates-page-test
  (:require [mftickets-web.components.templates-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.templates-page.handlers :as handlers]
            [mftickets-web.components.templates-page.reducers :as reducers]
            [mftickets-web.components.table-controllers.core :as components.table-controllers]))

(deftest test-init!

  (testing "Nil if we have http-response"
    (with-redefs [handlers/init (constantly ::foo)]
      (let [state (-> {} ((reducers/set-templates-http-response {:success? true})) atom)
            props {:state state}]
        (is (nil? (sut/init! props))))))

  (testing "Calls init handler if we don't have http-response"
    (with-redefs [handlers/init (fn [x] [::init x])]
      (let [state (atom {})
            props {:state state}]
        (is (= [::init props] (sut/init! props)))))))

(deftest test-table-controller

  (testing "Renders a table controller"
    (is (= components.table-controllers/table-controller
           (-> {:state (atom nil)} sut/table-controller first)))))

(deftest test-templates-page

  (with-redefs [sut/init! (constantly nil)]

    (testing "Renders a table controller"
      (let [props {:state (atom nil)}]
        (is (some #(= % [sut/table-controller props])
                  (tree-seq vector? identity (sut/templates-page props))))))))
