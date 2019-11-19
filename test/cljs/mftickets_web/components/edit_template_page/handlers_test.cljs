(ns mftickets-web.components.edit-template-page.handlers-test
  (:require [mftickets-web.components.edit-template-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]
            [mftickets-web.components.edit-template-page.reducers :as reducers]
            [mftickets-web.components.edit-template-page.queries :as queries]))

(deftest test-PickedTemplateChange

  (let [template {:id 1 :name "Foo"}
        old-state (-> {}
                      ((reducers/set-edited-template ::foo))
                      ((reducers/set-edited-template ::bar)))
        event (sut/->PickedTemplateChange template)
        reducer (events.protocols/reduce! event)
        new-state (reducer old-state)]

    (testing "Set's picked-template"
      (is (= template (queries/picked-template new-state))))

    (testing "Set's edited-template"
      (is (= template (queries/edited-template new-state))))))
