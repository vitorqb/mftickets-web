(ns mftickets-web.domain.template-section-test
  (:require [mftickets-web.domain.template-section :as sut]
            [cljs.test :refer-macros [is are deftest testing async use-fixtures]]))

(deftest test-gen-empty-template-section
  (let [temp-id 1232141
        template-id 999]
    (with-redefs [sut/gen-temp-id (constantly temp-id)]
      (is (= {:id nil
              :name ""
              :template-id template-id
              :is-multiple false
              :properties []
              ::sut/is-new? true
              ::sut/temp-id temp-id}
             (sut/gen-empty-template-section {:template-id template-id}))))))

