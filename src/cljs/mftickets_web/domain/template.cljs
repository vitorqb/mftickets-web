(ns mftickets-web.domain.template)

(defn gen-empty-template [{:keys [project-id]}]
  {:id nil
   :name ""
   :project-id project-id
   :creation-date nil
   :sections []})
