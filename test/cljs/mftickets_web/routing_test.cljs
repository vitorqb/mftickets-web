(ns mftickets-web.routing-test
  (:require [mftickets-web.routing :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [reitit.frontend]))

(deftest test-router->user-routing-opts
  (let [path1 "/foo"
        label1 "Foo"
        route1 [path1 {:name :foo :label label1}]
        path2 "/bar"
        label2 "Bar"
        route2 [path2 {:name :bar :label label2}]
        routes [route1 route2]
        router (reitit.frontend/router routes)]
    (is (= [{:label label1 :href path1} {:label label2 :href path2}]
           (sut/router->user-routing-opts router)))))
