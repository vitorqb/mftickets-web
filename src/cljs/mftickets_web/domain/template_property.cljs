(ns mftickets-web.domain.template-property)

(def ^:private id-counter (atom 0))
(defn- gen-new-property-id [] (->> (swap! id-counter inc) (str "new-")))

(defn get-id
  "Returns a unique id identifying a template property. Takes into account new properties."
  [property]
  (or (:id property) (::new-obj-id property)))

(defn same-id?
  "Returns true if two template properties have the same id. Takes into account new properties."
  [property1 property2]
  (= (get-id property1) (get-id property2)))

(defn gen-empty-template-property
  [{:keys [template-section-id]}]
  {:id nil
   :name ""
   :template-section-id template-section-id
   :is-multiple false
   :value-type "section.property.value.types/text"
   ::is-new? true
   ::new-obj-id (gen-new-property-id)})
