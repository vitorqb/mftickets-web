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

(deftest test-on-remove-template-property
  (let [property1 {:id 1}
        property2 {:id 2}
        property3 {:id 3}
        properties [property1 property2 property3]
        on-properties-change (fn [x] [::on-properties-change x])
        props {:template-properties-form/properties properties
               :template-properties-form.messages/on-properties-change on-properties-change
               :template-properties-form.impl/property property2}
        result (sut/on-remove-template-property props)]

    (is (= [::on-properties-change [property1 property3]] result))))
