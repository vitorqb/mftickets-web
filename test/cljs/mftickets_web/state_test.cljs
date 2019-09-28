(ns mftickets-web.state-test
  (:require [mftickets-web.state :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-swap

  (testing "Base"
    (let [atom (atom {::foo 1 ::bar 2})
          path [::bar]
          fatom (sut/->FocusedAtom atom path)]
      (is (= 5 (swap! fatom + 3)))
      (is (= @fatom 5))
      (is (= @atom {::foo 1 ::bar 5})))))

(deftest test-reset

  (testing "Base"
    (let [atom (atom {::foo {}})
          path [::foo ::bar ::baz]
          fatom (sut/->FocusedAtom atom path)]
      (is (= 1 (reset! fatom 1)))
      (is (= @fatom 1))
      (is (= @atom {::foo {::bar {::baz 1}}})))))
