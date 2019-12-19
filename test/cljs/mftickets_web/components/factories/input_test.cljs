(ns mftickets-web.components.factories.input-test
  (:require [mftickets-web.components.factories.input :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-DynamicMetadata
  (let [context {::foo 1}
        metadata-fn (fn [x] [::metadata x])
        dynamic-metadata (sut/->DynamicMetadata metadata-fn)]
    (is (= [::metadata context] (dynamic-metadata context)))))

(deftest test-assoc-messages

  (testing "No messages"
    (let [metadata {::foo 1}]
      (is (= metadata (sut/assoc-messages metadata)))))

  (testing "Empty messages"
    (let [metadata {:factories.input/messages {}}]
      (is (= metadata (sut/assoc-messages metadata)))))

  (testing "With messages and handlers"
    (let [on-change-handler (fn [x] [::on-change-handler x])
          handlers {::on-change-handler on-change-handler}
          messages {::on-change-message ::on-change-handler}
          metadata {:factories.input/messages messages
                    :factories.input/handlers handlers}]
      (is (= (assoc metadata ::on-change-message on-change-handler)
             (sut/assoc-messages metadata))))))

(deftest test-dissoc-metadata

  (testing "Empty"
    (let [m {::foo 1 :bar 2}]
      (is (= m (sut/dissoc-metadata m)))))

  (testing "Base"
    (let [m {::foo 1 :bar 2 :factories.input/component-kw :foo}]
      (is (= (dissoc m :factories.input/component-kw)
             (sut/dissoc-metadata m))))))

(deftest test-calculate-all-dynamic-metadata

  (testing "Empty"
    (is (= {} (sut/calculate-all-dynamic-metadata {} {}))))

  (testing "Base"
    (let [metadata-fn inc
          metadata {:foo 1 :bar (sut/->DynamicMetadata metadata-fn)}
          context 5]
      (is (= (assoc metadata :bar (metadata-fn context))
             (sut/calculate-all-dynamic-metadata metadata context))))))

(deftest test-input-factory

  (let [component-opts {:factories.input/component ::component
                        :factories.input/assoc-disabled? #(assoc %1 ::disabled %2)
                        :factories.input/assoc-value-to-props-fn #(assoc %1 ::value %2)}
        on-change-handler (fn [x] [::on-change x])
        handlers {::on-change-handler on-change-handler}
        messages {::on-change-message ::on-change-handler}
        parent-context {:selected-color "Blue"}
        metadata {:factories.input/component-kw ::component-kw
                  :factories.input/disabled? true
                  :factories.input/id :aaa
                  :factories.input/focus-value-fn ::foo
                  :factories.input/update-value-fn #(assoc %1 ::foo %2)
                  :factories.input/handlers handlers
                  :factories.input/messages messages
                  :factories.input/parent-context parent-context
                  :component/color (sut/->DynamicMetadata :selected-color)}
        parent-value {::foo 888}]

    (defmethod sut/input-factory-opts ::component-kw [_] component-opts)

    (testing "Returns component"
      (is (= ::component (-> (sut/input-factory metadata parent-value) first))))

    (testing "Component has key equal id in metadata"
      (is (= :aaa (-> (sut/input-factory metadata parent-value) meta :key))))

    (testing "Value is assoc"
      (is (= 888
             (-> (sut/input-factory metadata parent-value) second ::value))))

    (testing "Disabled is assoc if disable is true"
      (let [metadata* (assoc metadata :factories.input/disabled? true)]
        (is (true?
             (-> (sut/input-factory metadata* parent-value) second ::disabled)))))

    (testing "Do not disabled is assoc if disable is false"
      (let [metadata* (assoc metadata :factories.input/disabled? false)]
        (is (false?
             (-> (sut/input-factory metadata* parent-value) second ::disabled)))))

    (testing "Assocs the handler fn to the message"
      (is (= on-change-handler
             (-> (sut/input-factory metadata parent-value) second ::on-change-message))))

    (testing "Renders dynamic metadata"
      (is (= (:selected-color parent-context)
             (-> (sut/input-factory metadata parent-value) second :component/color))))))
