(ns mftickets-web.components.edit-project-page-test
  (:require [mftickets-web.components.edit-project-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.edit-project-page.reducers :as reducers]
            [mftickets-web.components.project-form :as components.project-form]
            [mftickets-web.components.edit-project-page.queries :as queries]
            [mftickets-web.components.message-box :as components.message-box]))

(deftest test-project-picker

  (testing "Nil if no picked project"
    (let [state {}
          props {:state (atom state)}]
      (is (nil? (sut/project-form props)))))

  (testing "Renders project-form if picked project"
    (let [project {:id 99 :name "Foo"}
          state (-> {} ((reducers/set-picked-project project)) atom)
          props {:state state}
          component (sut/project-form props)]
      (is (= components.project-form/project-form (first component)))
      (is (= project (-> component second :project-form/original-project))))))

(deftest test-message-box
  (testing "Shows message"
    (let [message "FOO"
          style :success]
      (with-redefs [queries/user-message (constantly [style message])]
        (is (= [components.message-box/message-box {:message message :style style}]
               (sut/message-box {:state (atom {})})))))))
