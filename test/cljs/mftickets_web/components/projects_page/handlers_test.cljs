(ns mftickets-web.components.projects-page.handlers-test
  (:require [mftickets-web.components.projects-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [cljs.core.async :as async]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.projects-page.queries :as queries]
            [mftickets-web.components.projects-page.reducers :as reducers]))

(deftest test-init

  (testing "Dispatches to fetch projects."
    (let [props {:state (atom {})}
          handler (sut/init props)]
      (with-redefs [sut/fetch-projects (fn [& xs] xs)]
        (is (= [[props]]
               (events.protocols/dispatch! handler)))))))

(deftest fetch-projects

  (let [props {:state (atom {})}
        handler (sut/fetch-projects props)]

    (testing "Dispatches to `fetch-projects.before`"
      (with-redefs [sut/fetch-projects--before (fn [& xs] xs)]
        (is (= [[props]] (events.protocols/dispatch! handler)))))))

(deftest fetch-projects--before

  (let [state (-> {} ((reducers/set-fetch-projects-response {:status 200})))
        props {:state state}
        handler (sut/fetch-projects--before state)
        reducer (events.protocols/reduce! handler)
        new-state (reducer state)]

    (is (false? (queries/loading? state)))
    (is (not (nil? (queries/fetch-projects-response state))))

    (is (true? (queries/loading? new-state)))
    (is (nil? (queries/fetch-projects-response new-state)))))

(deftest fetch-projects--after

  (let [state (-> {} ((reducers/set-loading? true)))
        props {:state state}
        projects [{:id 99}]
        response {:status 200 :success true :body [projects]}
        handler (sut/fetch-projects--after props response)
        reducer (events.protocols/reduce! handler)
        new-state (reducer state)]

    (is (nil? (queries/fetch-projects-response state)))
    (is (true? (queries/loading? state)))

    (is (= response (queries/fetch-projects-response new-state)))
    (is (false? (queries/loading? new-state)))))

(deftest fetch-projects--run-effects

  (let [state (atom {})
        projects [{:id 1 :name "Foo" :description "Bar"}]
        http {:get-projects #(async/go projects)}
        props {:state state :http http}
        handler (sut/fetch-projects props)]

    (async
     done
     (async/go
       (with-redefs [sut/fetch-projects--after (fn [& xs] (apply vector ::after xs))]
         (is (= [[::after props projects]]
                (async/<! (events.protocols/run-effects! handler))))
         (done))))))
