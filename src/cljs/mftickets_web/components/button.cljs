(ns mftickets-web.components.button
  "A simple button component."
  (:require [cljs.spec.alpha :as spec]))

;; Globals
(def ^:private known-styles #{:button/danger})

;; Specs
(spec/def :button/style (spec/nilable known-styles))
(spec/def :button/label (spec/nilable string?))

;; Css
(def ^:private base-class "button")
(def ^:private button-class (str base-class "__button"))
(def ^:private button-danger-class (str button-class "--danger"))

(defn- get-button-css-class [{:button/keys [style]}]
  {:pre [(or (nil? style) (known-styles style))]}
  (cond-> [button-class]
    (= style :button/danger) (conj button-danger-class)))

;; Components
(defn button
  [{:button/keys [label]
    :button.messages/keys [on-click]
    :as props}]

  {:pre [(ifn? on-click)]}
  
  [:div {:class base-class}
   [:div {:class (get-button-css-class props)
          :tabIndex 0
          :on-click (fn [&_] (on-click))
          :on-key-down #(when (-> % .-key (= "Enter")) (on-click))}
    label]])
