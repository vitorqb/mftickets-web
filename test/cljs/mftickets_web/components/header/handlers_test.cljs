(ns mftickets-web.components.header.handlers-test
  (:require [mftickets-web.components.header.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-RefreshAppMetadata
  (let [props {:events {:RefreshAppMetadata-> (constantly ::foo)}}
        event (sut/->RefreshAppMetadata props)]
    (is (= [::foo] (events.protocols/propagate! event)))))
