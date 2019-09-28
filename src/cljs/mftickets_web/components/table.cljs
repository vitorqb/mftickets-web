(ns mftickets-web.components.table
  (:require
   [com.rpl.specter :as s]))

(def base-class "table")
(def th-base-class (str base-class "__th"))
(def tr-base-class (str base-class "__tr"))
(def tr-header-modifier (str tr-base-class "--header"))
(def thead-base-class (str base-class "__thead"))

(defn- extract-th-values [props]
  (s/select [:table/config s/ALL :table/header] props))

(defn- expand-props [props]
  (assoc props
         :table/th-values (extract-th-values props)))

(defn- th
  "A th for the table."
  [{:table/keys [th-value]}]
  [:th {:class [th-base-class]}
   th-value])

(defn- thead
  "A thead for the table."
  [{:table/keys [th-values] :as props}]
  [:thead {:class [thead-base-class]}
   [:tr {:class [tr-base-class tr-header-modifier]}
    (for [th-value th-values
          :let [props* (assoc props :table/th-value th-value)]]
      ^{:key th-value} [th props*])]])

(defn- tr
  "A tr for the table."
  [{:table/keys [row config]}]
  [:tr {:class [tr-base-class]}
   (for [config-key (map :table/key config)
         :let [value (get row config-key "N/A")]]
     ^{:key config-key} [:td value])])

(defn- tbody
  "A tbody for the table."
  [{:table/keys [rows] :as props}]
  [:tbody
   (for [[row i] (map vector rows (range))
         :let [props* (assoc props :table/row row)]]
     ^{:key i} [tr props*])])

(defn table
  "A table component."
  [props]
  (let [props* (expand-props props)]
    [:table
     [thead props*]
     [tbody props*]]))
