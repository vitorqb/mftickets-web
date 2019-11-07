(ns mftickets-web.components.project-form-test
  (:require [mftickets-web.components.project-form :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.input :as components.input]))

(deftest test-render-input

  (let [edited-project {:id 1 :name "Foo"}
        props {:project-form/edited-project edited-project}
        metadata {:project-form.input/component :div
                  :project-form.input/id 1
                  :project-form.input/query-project-value-fn (constantly ::foo)
                  :project-form.input/assoc-project-value-fn (constantly ::bar)
                  :project-form.input/events-mapping {:InputChange ::baz}
                  :project-form.input/assoc-value-to-props-fn #(assoc %1 ::boz %2)
                  :div/disabled true}
        result (sut/render-input props metadata)
        [result-component result-props] result]

    (testing "Returns components"
      (is (= result-component :div)))

    (testing "New props are superset of old props"
      (is (every? (fn [[k v]] (= v (get result-props k))) metadata)))

    (testing "Events is assoced"
      (is (not (nil? (::baz (:events result-props))))))

    (testing "Value is assoced"
      (is (= ::foo (::boz result-props))))))

(deftest test-props->form-props

  (testing "Base"
    (let [form-props {::a ::b}
          props {:project-form/form-props form-props}
          result (sut/props->form-props props)]

      (testing "Assoc on-submit"
        (is (fn? (:on-submit result))))

      (testing "Keeps base"
        (is (= form-props (dissoc result :on-submit)))))))
