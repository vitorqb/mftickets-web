(ns mftickets-web.domain.template-section
  (:require [com.rpl.specter :as s]
            [mftickets-web.domain.sequences :as domain.sequences]))

(def ^:private id-counter (atom 0))
(defn- gen-temp-id [] (->> (swap! id-counter inc) (str "new-")))

(defn get-id
  "Returns a unique id identifying a template section."
  [section]
  (if (::is-new? section)
    (::temp-id section)
    (:id section)))

(defn same-id?
  "Returns true if two template sections have the same id, considering temporary ids."
  [section1 section2]
  (= (get-id section1) (get-id section2)))

(defn gen-empty-template-section
  [{:keys [template-id]}]
  {:id nil
   :name ""
   :template-id template-id
   :properties []
   ::is-new? true
   ::temp-id (gen-temp-id)})

(defn coll->section [section] (s/path (s/filterer #(same-id? section %)) s/FIRST))

(defn update-section-in-coll
  "Updates a section in a collection using `update-fn`."
  [coll section update-fn]
  (s/transform (coll->section section) update-fn coll))

(defn prepend-property-to-coll
  "Prepends a property to a specific section in a collection of sections."
  [coll section property]
  (s/setval [(coll->section section) :properties s/BEFORE-ELEM] property coll))

(defn update-properties-order-from-coll
  "Given a collection of sections, update `order` for all `properties` of a specific `section`."
  [coll section]
  (s/transform [(coll->section section) :properties] domain.sequences/update-order coll))
