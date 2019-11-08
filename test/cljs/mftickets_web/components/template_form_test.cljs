(ns mftickets-web.components.template-form-test
  (:require [mftickets-web.components.template-form :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-render-input

  (let [edited-template {:name "Foo"}
        props {:template-form/edited-template edited-template}
        metadata {:template-form.input/component ::component
                  :template-form.input/id ::id
                  :template-form.input/query-template-value-fn :name
                  :template-form.input/assoc-template-value-fn #(assoc %1 :name %2)
                  :template-form.input/assoc-value-to-props-fn #(assoc %1 :value %2)}
        result (sut/render-input props metadata)
        [r-component r-props] result]

    (testing "Renders the component"
      (is (= ::component r-component)))

    (testing "Assocs the value to the props"
      (is (= "Foo" (:value r-props))))

    (testing "Passes all metadata as props"
      (is (every? (fn [[k v]] (= (get r-props k) v)) metadata)))))

