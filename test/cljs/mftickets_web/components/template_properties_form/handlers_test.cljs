(ns mftickets-web.components.template-properties-form.handlers-test
  (:require [mftickets-web.components.template-properties-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-on-template-property-change
  (let [on-properties-change (fn [x] [::change x])
        property {:id 2 :name "BAR"}
        other-property {:id 1 :name "FOO"}
        properties [other-property property]
        update-value-fn #(assoc %1 :name %2)
        props {:template-properties-form.messages/on-properties-change on-properties-change
               :template-properties-form.impl/property property
               :template-properties-form/properties properties}
        metadata {:factories.input/update-value-fn update-value-fn}
        new-name "HHH"]
    (is (= [::change [other-property (assoc property :name new-name)]]
           (sut/on-template-property-change props metadata new-name)))))
