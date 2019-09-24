(ns mftickets-web.components.router-input-test
  (:require [mftickets-web.components.router-input :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.router-input.reducers :as reducers]))

(deftest test-input

  (testing "Passes value"
    (let [state (-> {} ((reducers/set-input-value "Foo")) atom)
          props {:state state}
          value (-> props sut/input second :value)]
      (is (= "Foo" value))))

  (testing "Passes on-change"
    (let [state (-> {} ((reducers/set-input-value "Foo")) atom)
          props {:state state}
          on-change (-> props sut/input second :on-change)]
      (is (= ((reducers/set-input-value "Bar") @state)
             (on-change "Bar"))))))

(deftest option-el

  (let [option {:label "Foo" :href "Bar"}]
    (testing "Non-selected."
      (is (= [:div {:class [sut/option-base-class]} [:a {:href "Bar"} "Foo"]]
             (sut/option-el {::sut/selected? false ::sut/option option}))))

    (testing "Selected."
      (is (= [:div {:class [sut/option-base-class sut/option-selected-modifier]}
              [:a {:href "Bar"} "Foo"]]
             (sut/option-el {::sut/selected? true ::sut/option option}))))))
