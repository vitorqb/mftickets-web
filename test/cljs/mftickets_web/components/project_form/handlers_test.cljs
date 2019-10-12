(ns mftickets-web.components.project-form.handlers-test
  (:require [mftickets-web.components.project-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-InputChange

  (testing "Set's path to value and propagates to parent EditedProjectChange->"
    (let [EditedProjectChange-> identity
          edited-project {:name "Foo"}
          props {:events {:EditedProjectChange-> EditedProjectChange->}
                 :project-form/edited-project edited-project}
          value "Bar"
          path [:name]
          new-edited-project {:name "Bar"}
          event (sut/->InputChange props {:input-path path :input-value value})]
      (is (= [new-edited-project] (events.protocols/propagate! event))))))
