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
  (let [state {}
        response {:status 200}
        new-state (-> state ((sut/after-edited-template-submit response)))]
    (is (false? (queries/loading? new-state)))
    (is (= response (queries/edited-template-submit-response new-state)))))
