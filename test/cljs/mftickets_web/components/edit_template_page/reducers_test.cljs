(ns mftickets-web.components.edit-template-page.reducers-test
  (:require [mftickets-web.components.edit-template-page.reducers :as sut]
            [cljs.test :refer-macros [is are deftest testing use-fixtures] :as t]
            [mftickets-web.components.edit-template-page.queries :as queries]))

(deftest test-before-edited-template-submit
  (let [state (-> {}
                  ((sut/set-loading? false))
                  ((sut/set-edited-template-submit-response {::foo 1}))
                  ((sut/before-edited-template-submit)))]
    (is (true? (queries/loading? state)))
    (is (nil? (queries/edited-template-submit-response state)))))

(deftest test-after-edited-template-submit
  (let [picked-template {:name "FOO"}
        edited-template {:name "BAR"}
        state (-> {}
                  ((sut/set-picked-template picked-template))
                  ((sut/set-edited-template edited-template)))]

    (testing "On success:"
      (let [response {:success true}
            new-state ((sut/after-edited-template-submit response) state)]

        (testing "Unsets loading"
          (is (false? (queries/loading? new-state))))

        (testing "Saves response"
          (is (= response (queries/edited-template-submit-response new-state))))

        (testing "Resets picked template"
          (is (nil? (queries/picked-template new-state))))

        (testing "Resets edited template"
          (is (nil? (queries/edited-template new-state))))))

    (testing "On error:"
      (let [response {:success false}
            new-state ((sut/after-edited-template-submit response) state)]

        (testing "Unsets loading"
          (is (false? (queries/loading? new-state))))

        (testing "Saves response"
          (is (= response (queries/edited-template-submit-response new-state))))

        (testing "Do not reset picked template"
          (is (= picked-template (queries/picked-template new-state))))

        (testing "Do not Resets edited template"
          (is (= edited-template (queries/edited-template new-state))))))))
