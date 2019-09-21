(ns mftickets-web.domain.matcher-test
  (:require [mftickets-web.domain.matcher :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-matching-options

  (testing "Base"
    
    (let [options ["Foo Bar" "Foo Bar Baz" "Cachorro" "Hello World Foo Bar"]]
      (are [s options*] (= options* (sut/matching-options options s))
        "Foo" ["Foo Bar" "Foo Bar Baz" "Hello World Foo Bar"]
        "Bar" ["Foo Bar" "Foo Bar Baz" "Hello World Foo Bar"]
        "Baz" ["Foo Bar Baz"]
        "Foo Bar" ["Foo Bar" "Foo Bar Baz" "Hello World Foo Bar"]
        "World Bar" ["Hello World Foo Bar"]
        "world bar" ["Hello World Foo Bar"])))

  (testing "Custom get-str-fn"
    (let [options [{:label "Foo"} {:label "Bar"}]]
      (is (= [{:label "Foo"}]
             (sut/matching-options options "f" {:get-str-fn :label}))))))
