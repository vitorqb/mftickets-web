(ns mftickets-web.components.template-picker.handlers-test
  (:require [mftickets-web.components.template-picker.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-ValueChange

  (testing "Propagates to ValueChange->"
    (let [picked-template {:id 99 :name "Foo"}
          picked-option {:label "Foo" :value picked-template}
          ValueChange-> #(when (= picked-template %) ::foo)
          props {:events {:ValueChange-> ValueChange->}}
          event (sut/->ValueChange props picked-option)]
      (is (= [::foo] (events.protocols/propagate! event))))))
