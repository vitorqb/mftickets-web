(ns mftickets-web.domain.template-section)

(def ^:private id-counter (atom 0))
(defn- gen-temp-id [] (->> (swap! id-counter inc) (str "new-")))

(defn get-id
  "Returns a unique id identifying a template section."
  [section]
  (if (::is-new? section)
    (::temp-id section)
    (:id section)))

(defn gen-empty-template-section
  [{:keys [template-id]}]
  {:id nil
   :name ""
   :template-id template-id
   :is-multiple false
   :properties []
   ::is-new? true
   ::temp-id (gen-temp-id)})
