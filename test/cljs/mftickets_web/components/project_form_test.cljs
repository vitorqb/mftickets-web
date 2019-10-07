(ns mftickets-web.components.project-form-test
  (:require [mftickets-web.components.project-form :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.input :as components.input]))

(deftest test-render-input

  (testing "Base"
    (let [edited-project {::foo "BAR"}
          props {:project-form/edited-project edited-project}
          id :id
          label "label"
          path ::foo
          disabled true
          metadata {:id id :label label :path path :disabled disabled}
          response (sut/render-input props metadata)]
      (is (= components.input/input (first response)))
      (is (= {:label label :value (::foo edited-project) :disabled disabled :parent-props props}
             (dissoc (second response) :events)))
      (is (= (-> response meta :key) id)))))

(deftest test-props->form-props

  (testing "Base"
    (let [form-props {::a ::b}
          props {:project-form/form-props form-props}
          result (sut/props->form-props props)]

      (testing "Assoc on-submit"
        (is (fn? (:on-submit result))))

      (testing "Keeps base"
        (is (= form-props (dissoc result :on-submit)))))))
