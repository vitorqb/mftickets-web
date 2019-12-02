(ns mftickets-web.components.edit-template-page.handlers-test
  (:require [mftickets-web.components.edit-template-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.edit-template-page.reducers :as reducers]
            [mftickets-web.components.edit-template-page.queries :as queries]))

(deftest test-on-picked-template

  (let [template {:id 1 :name "Foo"}
        state (-> {}
                  ((reducers/set-picked-template ::foo))
                  ((reducers/set-edited-template ::bar))
                  atom)
        props {:state state}]

    (sut/on-template-picked props template)

    (testing "Set's picked-template"
      (is (= template (queries/picked-template @state))))

    (testing "Set's edited-template"
      (is (= template (queries/edited-template @state))))))


(deftest test-on-edited-template-change
  (let [state (atom {})
        new-template {:id 99}
        e-state (-> @state ((reducers/set-edited-template new-template)))]
    (sut/on-edited-template-change {:state state} new-template)
    (is (= e-state @state))))
