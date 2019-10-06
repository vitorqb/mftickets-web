(ns mftickets-web.components.create-project-page-test
  (:require [mftickets-web.components.create-project-page :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.message-box :as components.message-box]
            [mftickets-web.components.create-project-page.queries :as queries]))


(deftest test-message-box
  
  (testing "Nil if not message"
    (with-redefs [queries/user-message (constantly nil)]
      (is (nil? (sut/message-box {:state (atom {})})))))

  (testing "Renders message box if message"
    (with-redefs [queries/user-message (constantly [:error "Foo"])]
      (is (= [components.message-box/message-box {:style :error :message "Foo"}]
             (sut/message-box {:state (atom {})}))))))
