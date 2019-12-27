(ns mftickets-web.components.template-form.handlers-test
  (:require [mftickets-web.components.template-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.domain.template-section :as domain.template-section]
            [mftickets-web.domain.sequences :as domain.sequences]))

(deftest test-on-input-change
  (let [edited-template {:id 999 :name "Foo"}
        update-value-fn #(assoc %1 :name %2)
        on-edited-template-change #(do [::edited-template-change %])
        props {:template-form/edited-template edited-template
               :template-form.messages/on-edited-template-change on-edited-template-change}
        metadata {:factories.input/update-value-fn update-value-fn}
        new-value "Bar"]
    (is (= [::edited-template-change (assoc edited-template :name new-value)]
           (sut/on-input-change props metadata new-value)))))

(deftest on-add-template-section
  (let [new-section {:id 1}]
    (with-redefs [domain.template-section/gen-empty-template-section (constantly new-section)]
      (let [template-id 777
            old-section {:id 888}
            edited-template {:id 999
                             :sections [old-section]
                             :template-id template-id}
            exp-sections (domain.sequences/update-order [new-section old-section])
            exp-new-edited-template (assoc edited-template :sections exp-sections)
            on-edited-template-change (fn [x] [::template-change x])
            props {:template-form.messages/on-edited-template-change on-edited-template-change
                   :template-form/edited-template edited-template}
            result (sut/on-add-template-section props)]

        (is (= result [::template-change exp-new-edited-template]))))))
