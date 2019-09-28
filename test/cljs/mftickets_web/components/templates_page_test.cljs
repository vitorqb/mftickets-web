(ns mftickets-web.components.templates-page-test
  (:require [mftickets-web.components.templates-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.templates-page.handlers :as handlers]
            [mftickets-web.components.templates-page.reducers :as reducers]
            [mftickets-web.events :as events]))

(deftest test-init!

  (testing "Nil if we have http-response"
    (with-redefs [events/react! (constantly ::foo)]
      (let [state (-> {} ((reducers/set-templates-http-response {:success? true})) atom)
            props {:state state}]
        (is (nil? (sut/init! props))))))

  (testing "Calls react! if we don't have http-response"
    (with-redefs [events/react! (fn [props handler] [props handler])
                  handlers/init! (fn [props] {:handlers.init!/props props})]
      (let [state (atom {})
            props {:state state}]
        (is (= (sut/init! props)
               [props {:handlers.init!/props props}]))))))
