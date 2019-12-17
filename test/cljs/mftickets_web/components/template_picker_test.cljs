(ns mftickets-web.components.template-picker-test
  (:require [mftickets-web.components.template-picker :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [cljs.core.async :as async]
            [mftickets-web.components.select :as components.select]))

(deftest test-template->select-option
  (let [name "foo" template {:id 1 :name name}]
    (is (= {:label name :value template} (sut/template->select-option template)))))

(deftest test-async-select

  (testing "Renders message box if no project"
    (is (= [sut/no-project-selected-message-box]
           (sut/async-select {}))))

  (testing "Renders an async-select if project"
    (let [http {:get-matching-templates (constantly (async/go []))}
          state (atom {})
          project-id 1
          props {:http http
                 :state state
                 :template-picker/project-id project-id}]
      (is (= (first (sut/async-select props))
             components.select/async-select)))))

(deftest test-get-matching-options
  (async
   done
   (async/go
     (let [project-id 1
           input-value "f"
           template {:id 1 :name "Foo"}
           get-matching-templates #(async/go (and (= (:name-like %) input-value) [template]))
           http {:get-matching-templates get-matching-templates}
           props {:http http :template-picker/project-id project-id}
           get-matching-options (sut/get-matching-options props)
           matching-options (async/<! (get-matching-options input-value))]
       (is (= [{:label (:name template) :value template}]
              matching-options))
       (done)))))
