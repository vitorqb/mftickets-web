(ns mftickets-web.components.edit-project-page.reducers
  (:require [mftickets-web.components.edit-project-page.queries :as queries]))

(defn set-picked-project [x] #(assoc % :picked-project x))

(defn set-edited-project [x] #(assoc % :edited-project x))

(defn set-loading? [x] #(assoc % :loading? x))

(defn set-edit-project-response [edit-project-response]
  #(assoc % :edit-project-response edit-project-response))

(defn edit-project-response
  "Reduces responding to a new edit project response."
  [edit-project-response]
  (if (:success edit-project-response)
    (let [new-project (:body edit-project-response)]
      (comp (set-edit-project-response edit-project-response)
            (set-picked-project new-project)
            (set-edited-project nil)))
    (set-edit-project-response edit-project-response)))

(defn new-picked-project
  "Reducers to a new picked project"
  [new-picked-project]
  (fn [state]
    (if (= (queries/picked-project state) new-picked-project)
      state
      (-> state
          ((set-picked-project new-picked-project))
          ((set-edited-project nil))
          ((set-edit-project-response nil))))))
