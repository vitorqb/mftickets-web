(ns mftickets-web.components.edit-project-page.handlers-test
  (:require [mftickets-web.components.edit-project-page.handlers :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]
            [mftickets-web.components.edit-project-page.reducers :as reducers]))

(deftest test-before-edited-project-submit
  (let [state (atom {})
        props {:state state}
        e-state (-> @state
                    ((reducers/set-loading? true))
                    ((reducers/set-edit-project-response nil)))]
    (sut/before-edited-project-submit props)
    (is (= e-state @state))))

(deftest test-after-edited-project-submit
  (let [state (atom {})
        props {:edit-project-page.messages/refresh-app-metadata (constantly ::refresh)
               :state state}
        response {:status 204}
        e-state (-> @state
                    ((reducers/set-loading? false))
                    ((reducers/set-edit-project-response response)))]
    (sut/after-edited-project-submit props response)
    (is (= e-state @state))))

(deftest test-on-project-form-edited-project-change
  (let [state (atom {})
        props {:state state}
        new-edited-project {:name "Foo"}
        e-new-state (-> @state ((reducers/set-edited-project new-edited-project)))]
    (is (= e-new-state (sut/on-project-form-edited-project-change props new-edited-project)))
    (is (= e-new-state @state))))
