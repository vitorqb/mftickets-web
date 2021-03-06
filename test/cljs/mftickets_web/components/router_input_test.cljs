(ns mftickets-web.components.router-input-test
  (:require [mftickets-web.components.router-input :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.router-input.reducers :as reducers]))

(deftest test-input

  (testing "Passes value"
    (let [state (-> {} ((reducers/set-input-value "Foo")) atom)
          props {:state state}
          value (-> props sut/input second :input/value)]
      (is (= "Foo" value))))

  (testing "Passes OnChange->"
    (let [init-state (-> {} ((reducers/set-input-value "Foo")))
          state (atom init-state)
          props {:state state}
          on-change (-> props sut/input second :input.messages/on-change)]
      (on-change "Bar")
      (is (= ((reducers/set-input-value "Bar") init-state) @state)))))

(deftest option-el

  (let [option {:label "Foo" :href "Bar"}]
    (testing "Non-selected."
      (is (= [:div {:class [sut/option-base-class]} [:a {:href "Bar"} "Foo"]]
             (sut/option-el {::sut/selected? false ::sut/option option}))))

    (testing "Selected."
      (is (= [:div {:class [sut/option-base-class sut/option-selected-modifier]}
              [:a {:href "Bar"} "Foo"]]
             (sut/option-el {::sut/selected? true ::sut/option option}))))))

(deftest test-get-selected-option

  (let [matching-options [{:label "Foo" :href "1"} {:label "Bar" :href "2"}]
        selected-el-index 1
        props {:router-input/matching-options matching-options
               :router-input/selected-el-index selected-el-index}]
    (is (= {:label "Bar" :href "2"}
           (sut/get-selected-option props)))))
