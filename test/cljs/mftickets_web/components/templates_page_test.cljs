(ns mftickets-web.components.templates-page-test
  (:require [mftickets-web.components.templates-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.templates-page.handlers :as handlers]
            [mftickets-web.components.templates-page.reducers :as reducers]))

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
