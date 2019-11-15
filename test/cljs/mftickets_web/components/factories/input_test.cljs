(ns mftickets-web.components.factories.input-test
  (:require [mftickets-web.components.factories.input :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-input-factory

  (let [parent-props {::parent-props true}
        metadata {:factories.input/component ::component
                  :factories.input/assoc-disabled? #(assoc %1 ::disabled %2)
                  :factories.input/disabled? true
                  :factories.input/id 999
                  :factories.input/focus-value-fn ::foo
                  :factories.input/update-value-fn #(assoc %1 ::foo %2)
                  :factories.input/assoc-value-to-props-fn #(assoc %1 ::value %2)}
        parent-value {::foo 888}]

    (testing "Returns component"
      (is (= ::component (-> (sut/input-factory parent-props metadata parent-value) first))))

    (testing "Component has key equal id in metadata"
      (is (= 999 (-> (sut/input-factory parent-props metadata parent-value) meta :key))))

    (testing "Parent props is assoced"
      (is (= parent-props
             (-> (sut/input-factory parent-props metadata parent-value) second :parent-props))))

    (testing "Value is assoc"
      (is (= 888
             (-> (sut/input-factory parent-props metadata parent-value) second ::value))))

    (testing "Disabled is assoc if disable is true"
      (let [metadata* (assoc metadata :factories.input/disabled? true)]
        (is (true?
             (-> (sut/input-factory parent-props metadata* parent-value) second ::disabled)))))

    (testing "Do not disabled is assoc if disable is false"
      (let [metadata* (assoc metadata :factories.input/disabled? false)]
        (is (false?
             (-> (sut/input-factory parent-props metadata* parent-value) second ::disabled)))))))
