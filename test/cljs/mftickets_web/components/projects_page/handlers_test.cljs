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
          handler (sut/->Init props)]
      (is (= [(sut/->FetchProjects props)]
             (events.protocols/dispatch! handler))))))

(deftest FetchProjects

  (let [props {:state (atom {})}
        handler (sut/->FetchProjects props)]

    (testing "Dispatches to `FetchProjects--before`"
      (is (= [(sut/->FetchProjects--before)]
             (events.protocols/dispatch! handler))))))

(deftest FetchProjects--before

  (let [state (-> {} ((reducers/set-fetch-projects-response {:status 200})))
        props {:state state}
        handler (sut/->FetchProjects--before)
        reducer (events.protocols/reduce! handler)
        new-state (reducer state)]

    (is (false? (queries/loading? state)))
    (is (not (nil? (queries/fetch-projects-response state))))

    (is (true? (queries/loading? new-state)))
    (is (nil? (queries/fetch-projects-response new-state)))))

(deftest FetchProjects--after

  (let [state (-> {} ((reducers/set-loading? true)))
        projects [{:id 99}]
        response {:status 200 :success true :body [projects]}
        handler (sut/->FetchProjects--after response)
        reducer (events.protocols/reduce! handler)
        new-state (reducer state)]

    (is (nil? (queries/fetch-projects-response state)))
    (is (true? (queries/loading? state)))

    (is (= response (queries/fetch-projects-response new-state)))
    (is (false? (queries/loading? new-state)))))

(deftest FetchProjects--run-effects

  (let [state (atom {})
        projects [{:id 1 :name "Foo" :description "Bar"}]
        response {:body projects}
        http {:get-projects #(async/go response)}
        props {:state state :http http}
        handler (sut/->FetchProjects props)]

    (async
     done
     (async/go
       (is (= [(sut/->FetchProjects--after response)]
              (async/<! (events.protocols/run-effects! handler))))
       (done)))))
