(ns mftickets-web.components.template-sections-form-test
  (:require [mftickets-web.components.template-sections-form :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-render-input

  (let [section {:id 1}
        props {::sut/section section}
        metadata {:factories.input/component ::div
                  :factories.input/id ::id
                  :factories.input/focus-value-fn :id
                  :factories.input/update-value-fn #(assoc %1 :id %2)
                  :factories.input/assoc-value-to-props-fn #(assoc %1 :value %2)}
        result (sut/render-input props metadata)
        [r-component r-props] result]

    (testing "Renders the component"
      (is (= r-component ::div)))

    (testing "Assocs the value to the props"
      (is (= 1 (:value r-props))))

    (testing "Passes all metadata as props"
      (is (every? (fn [[k v]] (= (get r-props k) v)) metadata)))))
