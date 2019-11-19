(ns mftickets-web.components.edit-template-page.queries-test
  (:require [mftickets-web.components.edit-template-page.queries :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.edit-template-page.reducers :as reducers]))

(deftest test-picked-template

  (let [picked-template {:id 1}
        state (-> {} ((reducers/set-picked-template picked-template)))]
    (is (= picked-template (sut/picked-template state)))))

(deftest test-edited-template

  (let [edited-template {:id 1}
        state (-> {} ((reducers/set-edited-template edited-template)))]
    (is (= edited-template (sut/edited-template state)))))
