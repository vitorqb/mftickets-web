(ns mftickets-web.components.edit-template-page-test
  (:require [mftickets-web.components.edit-template-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.template-picker :as components.template-picker]
            [mftickets-web.components.edit-template-page.reducers :as reducers]
            [clojure.walk :as walk]
            [mftickets-web.components.template-form :as components.template-form]
            [mftickets-web.components.message-box :as components.message-box]
            [mftickets-web.components.edit-template-page.queries :as queries]))

(deftest test-loading-wrapper

  (testing "Nil if not loading"
    (let [state (-> {} ((reducers/set-loading? false)) atom)]
      (is (nil? (sut/loading-wrapper {:state state})))))

  (testing "Renders if loading"
    (let [state (-> {} ((reducers/set-loading? true)) atom)]
      (is (= [:div {:class [sut/loading-wrapper-class]} "Loading..."]
             (sut/loading-wrapper {:state state}))))))

(deftest test-message-box

  (testing "Nil if not user message"
    (with-redefs [queries/user-message (constantly nil)]
      (is (nil? (sut/message-box {:state (atom {})})))))

  (testing "Renders message box if user message"
    (let [message {:message "foo" :style :bar}]
      (with-redefs [queries/user-message (constantly message)]
        (let [props {:state (atom {})}
              [r-component r-props] (sut/message-box props)]
          (is (= r-component components.message-box/message-box))
          (is (= r-props message)))))))

(deftest test-template-picker

  (let [project-id 99
        picked-template {:id 88 :project-id project-id}
        http {}
        state (atom (-> {} ((reducers/set-picked-template picked-template))))
        props {:edit-template-page/project-id project-id
               :edit-project-page/picked-template picked-template
               :http http
               :state state}
        [r-component r-props] (sut/template-picker props)]

    (testing "Renders a template picker"
      (is (= components.template-picker/template-picker r-component)))

    (testing "Passes project id"
      (is (= project-id (-> r-props :template-picker/project-id))))

    (testing "Passes picked template"
      (is (= picked-template (-> r-props :template-picker/picked-template))))))

(deftest test-edit-template-page

  (let [props {:state (reify IDeref (-deref [_] nil))}
        [r-component r-props & r-children] (sut/edit-template-page props)]

    (testing "Renders a div with correct class"
      (is (= :div r-component))
      (is (= sut/base-class (:class r-props))))

    (testing "Renders the template-picker"
      (is (some #(= % [sut/template-picker props])
                (tree-seq #(or (seq? %) (vector? %)) identity r-children))))))

(deftest test-template-form

  (let [picked-template {:id 1 :name "Foo"}
        edited-template {:id 2 :name "Bar"}
        state (-> {}
                  ((reducers/set-picked-template picked-template))
                  ((reducers/set-edited-template edited-template))
                  atom)
        props {:state state}
        [r-component r-props] (sut/template-form props)]

    (testing "Don't render if no picked-template"
      (let [state* (-> @state ((reducers/set-picked-template nil)) atom)
            props* (assoc props :state state*)]
        (is (nil? (sut/template-form props*)))))

    (testing "Renders a template form"
      (is (= components.template-form/template-form r-component)))

    (testing "Passes :picked-template to :original-template"
      (is (= picked-template (:template-form/original-template r-props))))

    (testing "Passes :edited-template to :edited-template"
      (is (= edited-template (:template-form/edited-template r-props))))))
