(ns mftickets-web.components.template-form-test
  (:require [mftickets-web.components.template-form :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.template-form.inputs :as inputs]
            [mftickets-web.components.form :as components.form]))

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

(deftest test-template-form

  (let [inputs-metadatas [inputs/id]
        edited-template {:id 1 :name "Foo"}
        props {:template-form/inputs-metadatas inputs-metadatas
               :template-form/edited-template edited-template
               :template-form/original-template edited-template}]

    (testing "Null if no edited-template"
      (let [props* (assoc props :template-form/edited-template nil)
            result (sut/template-form props*)]
        (is (nil? result))))

    (testing "Renders a form"
      (let [[r-component _ _] (sut/template-form props)]
        (is (= r-component components.form/form))))

    (testing "Uses `render-input` to render the children."
      (with-redefs [sut/render-input (fn [& xs] xs)]
        (let [[_ _ & children] (sut/template-form props)]
          (is (= 1 (count children)))
          (is (= [[props inputs/id]] (first children))))))))
