(ns mftickets-web.components.project-form-test
  (:require [mftickets-web.components.project-form :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.input :as components.input]
            [mftickets-web.components.factories.input :as factories.input]))

(deftest test-render-input

  (let [component-opts {:factories.input/component :div
                        :factories.input/assoc-value-to-props-fn #(assoc %1 ::boz %2)
                        :factories.input/assoc-disabled? #(assoc %1 ::disabled %2)}]

    (defmethod factories.input/input-factory-opts ::foo [_] component-opts)

    (let [edited-project {:id 1 :name "Foo"}
          props {:project-form/edited-project edited-project}
          metadata {:factories.input/component-kw ::foo
                    :factories.input/id 1
                    :factories.input/focus-value-fn (constantly ::foo)
                    :factories.input/update-value-fn (constantly ::bar)
                    :factories.input/messages
                    {:input.messages/on-change :project-form.handlers/on-input-change}
                    :div/disabled true}
          result (sut/render-input props metadata)
          [result-component result-props] result]

      (testing "Returns components"
        (is (= result-component :div)))

      (testing "On change message is assoced."
        (is (fn? (:input.messages/on-change result-props))))

      (testing "Value is assoced"
        (is (= ::foo (::boz result-props)))))))

(deftest test-props->form-props

  (testing "Base"
    (let [form-props {::a ::b}
          props {:project-form.messages/on-edited-project-submit (constantly nil)
                 :project-form/form-props form-props}
          result (sut/props->form-props props)]

      (testing "Assoc on-submit"
        (is (fn? (:on-submit result))))

      (testing "Keeps base"
        (is (= form-props (dissoc result :on-submit)))))))
