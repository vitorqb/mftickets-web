(ns mftickets-web.components.template-sections-form-test
  (:require [mftickets-web.components.template-sections-form :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.factories.input :as factories.input]))

(deftest test-render-input

  (let [component-opts {:factories.input/component ::div
                        :factories.input/assoc-disabled? #(assoc %1 ::disabled %2)
                        :factories.input/assoc-value-to-props-fn #(assoc %1 :value %2)}]

    (defmethod factories.input/input-factory-opts ::component [_] component-opts)

    (let [section {:id 1}
          props {:template-sections-form.impl/section section}
          metadata {:factories.input/component-kw ::component
                    :factories.input/id ::id
                    :factories.input/focus-value-fn :id
                    :factories.input/update-value-fn #(assoc %1 :id %2)
                    :factories.input/messages
                    {:input.messages/on-change
                     :template-sections-form.handlers/on-template-section-input-change}}
          result (sut/render-input props metadata)
          [r-component r-props] result]

      (testing "Renders the component"
        (is (= r-component ::div)))

      (testing "Assocs the value to the props"
        (is (= 1 (:value r-props))))

      (testing "Assocs messages from :factories.input/messages"
        (is (ifn? (:input.messages/on-change r-props)))))))
