(ns mftickets-web.components.current-page-test
  (:require [mftickets-web.components.current-page :as sut]
             [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-page

  (testing "Render login page if not logged in"
    (let [login-page [:div "Login Page"]
          props #:current-page{:logged-in? false :instances {:login-page login-page}}]
      (is (= login-page (sut/current-page props)))))

  (testing "If logged in..."

    (let [instances {:header [:header#header]
                     :router-dialog [:div#router-dialog]
                     :page [:div#page]}
          injections {::foo 1}
          props #:current-page{:logged-in? true :instances instances :injections injections}
          current-page (sut/current-page props)]

      (testing "Renders header"
        (is (= (:header instances) (get current-page 2))))

      (testing "Renders router-dialog"
        (is (= (:router-dialog instances) (get current-page 1))))

      (testing "Renders page"
        (is (= [(:page instances) injections] (get current-page 3)))))))

