(ns mftickets-web.components.template-properties-form.handlers-test
  (:require [mftickets-web.components.template-properties-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.domain.sequences :as domain.sequences]
            [clojure.set]
            [mftickets-web.domain.template-property :as domain.template-property]))

(deftest test-on-template-property-change
  (let [on-properties-change (fn [x] [::change x])
        property {:id 2 :name "BAR" :order 1}
        other-property {:id 1 :name "FOO" :order 0}
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

(deftest test-on-move-template-property-back

  (let [property1 {:id 1}
        property2 {:id 2}
        property3 {:id 3}
        properties [property1 property2 property3]
        props {:template-properties-form.messages/on-properties-change identity}]

    (testing "If on first position, keep it."
      (let [props*
            (assoc props
                   :template-properties-form.impl/property property1
                   :template-properties-form/properties [property1 property2 property3])]
        (is (= (domain.sequences/update-order properties)
               (sut/on-move-template-property-back props*)))))

    (testing "If on second position, move it up."
      (let [props*
            (assoc props
                   :template-properties-form.impl/property property2
                   :template-properties-form/properties [property1 property2 property3])]
        (is (= (domain.sequences/update-order [property2 property1 property3])
               (sut/on-move-template-property-back props*)))))

    (testing "If on third position, move it up."
      (let [props*
            (assoc props
                   :template-properties-form.impl/property property3
                   :template-properties-form/properties [property1 property2 property3])]
        (is (= (domain.sequences/update-order [property1 property3 property2])
               (sut/on-move-template-property-back props*)))))))

(deftest test-on-move-template-property-forward
  
  (testing "Base"
    (let [property1 {:id 1}
          property2 {:id 2}
          property3 {:id 3}
          properties [property1 property2 property3]
          props {:template-properties-form.messages/on-properties-change identity
                 :template-properties-form.impl/property property1
                 :template-properties-form/properties properties}]
      (is (= (domain.sequences/update-order [property2 property1 property3])
             (sut/on-move-template-property-forward props)))))

  (testing "Newly created properties"
    (let [property1 {::domain.template-property/new-obj-id 1}
          property2 {::domain.template-property/new-obj-id 2}
          property3 {::domain.template-property/new-obj-id 3}
          properties [property1 property2 property3]
          props {:template-properties-form.messages/on-properties-change identity
                 :template-properties-form.impl/property property2
                 :template-properties-form/properties properties}]
      (is (= (domain.sequences/update-order [property1 property3 property2])
             (sut/on-move-template-property-forward props))))))
