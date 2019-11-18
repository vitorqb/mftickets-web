(ns mftickets-web.components.template-form.handlers-test
  (:require [mftickets-web.components.template-form.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.events.protocols :as events.protocols]))

(deftest test-InputChange

  (let [edited-template {:id 999 :name "Foo"}
        update-value-fn #(assoc %1 :name %2)
        EditedTemplateChange #(do [::edited-template-change %])
        events {:EditedTemplateChange-> EditedTemplateChange}
        props {:template-form/edited-template edited-template :events events}
        metadata {:factories.input/update-value-fn update-value-fn}
        new-value "Bar"
        handler (sut/->InputChange props metadata new-value)
        propagated (events.protocols/propagate! handler)]

    (testing "Propagates EditedTemplateChange with updated value"
      (is (= [[::edited-template-change (assoc edited-template :name "Bar")]]
             propagated)))))
