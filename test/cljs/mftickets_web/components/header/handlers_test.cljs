(ns mftickets-web.components.header.handlers-test
  (:require [mftickets-web.components.header.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-display-router
  (let [props {:events {:display-router-dialog-> (constantly ::foo)}}
        event (sut/display-router props)]
    (is (= [::foo] (events.protocols/propagate! event)))))
