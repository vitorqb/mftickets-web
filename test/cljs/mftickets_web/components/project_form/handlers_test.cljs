(ns mftickets-web.components.project-form.handlers-test
  (:require [mftickets-web.components.project-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-InputChange

  (testing "Set's new-value and propagates"
    (let [EditedProjectChange-> identity
          edited-project {:name "Foo"}
          props {:events {:EditedProjectChange-> EditedProjectChange->}
                 :project-form/edited-project edited-project}
          value "Bar"
          assoc-project-value-fn #(assoc %1 :name %2)
          input-metadata {:factories.input/update-value-fn assoc-project-value-fn}
          new-edited-project {:name value}
          event (sut/->InputChange props input-metadata value)]
      (is (= [new-edited-project]
             (events.protocols/propagate! event))))))
