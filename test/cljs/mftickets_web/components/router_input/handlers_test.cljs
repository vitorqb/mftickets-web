(ns mftickets-web.components.router-input.handlers-test
  (:require [mftickets-web.components.router-input.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.router-input.reducers :as reducers]))

(deftest test-on-input-change
  (let [state (atom {})
        new-value "FOO"
        e-new-state (-> @state ((reducers/set-input-value new-value)))]
    (sut/on-input-change {:state state} new-value)
    (is (= e-new-state @state))))

(deftest on-arrow-input-key-up

  (testing "Reducers state using select-from-key"
    (let [state (atom {})
          matching-options ["FOO"]
          props {:state state :router-input/matching-options matching-options}
          key "ArrowDown"
          e-state (-> @state ((reducers/select-from-key matching-options key)))]
      (sut/on-arrow-input-key-up props key)
      (is (= @state e-state)))))

(deftest on-enter-input-key-up

  (let [navigate ::navigate
        close-router-dialog ::close-router-dialog
        selected-option {:href "foo"}
        props {:router-input.messages/navigate navigate
               :router-input.messages/close-router-dialog close-router-dialog
               :router-input/selected-option selected-option}]

    (testing "No effects when key is not enter"
      (is (nil? (sut/on-enter-input-key-up* props "NOT ENTER"))))

    (testing "Calls navigate and close router dialog if it is enter"
      (is (= [[navigate "foo"] [close-router-dialog]]
             (sut/on-enter-input-key-up* props "Enter"))))))
