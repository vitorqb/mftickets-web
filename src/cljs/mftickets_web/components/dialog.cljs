(ns mftickets-web.components.dialog
  (:require
   [mftickets-web.components.dialog.queries :as queries]
   [mftickets-web.components.dialog.reducers :as reducers]
   [mftickets-web.components.dialog.handlers :as handlers]))

(def base-class "dialog")
(def base-disabled-modifier (str base-class "--disabled"))
(def contents-class (str base-class "__content"))
(def header-class (str base-class "__header"))
(def close-btn-class (str base-class "__close-btn"))
(def body-btn-class (str base-class "__body"))

(defn- get-class
  "Returns the class for a dialog given it's props."
  [{:keys [state]}]
  (let [disabled? (queries/disabled? @state)]
    (cond-> [base-class]
      disabled? (conj base-disabled-modifier))))

(defn- close-btn
  "A button to close the dialog."
  [props]
  [:button {:class close-btn-class
            :on-click #(handlers/close props)}
   "X"])

(defn dialog
  "A dialog component."
  [{:dialog/keys [body-component] :as props}]
  [:div {:class (get-class props)}
   [:div {:class contents-class}
    [:div {:class header-class}
     [close-btn props]]
    [:div {:class body-btn-class}
     body-component]]])
