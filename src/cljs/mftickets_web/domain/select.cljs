(ns mftickets-web.domain.select
  "Helper namespace for handling options for the Select component."
  (:require [cljs.spec.alpha :as spec]))

(def boolean-options [{:value true :label "True"} {:value false :label "False"}])

(defn boolean->option
  "Converts a boolean into an option."
  [x]
  {:pre [(boolean? x)] :post [((set boolean-options) %)]}
  (if x {:value true :label "True"} {:value false :label "False"}))

(defn option->boolean
  "Converts an option into a boolean."
  [x]
  {:pre [((set boolean-options) x)] :post [(boolean? %)]}
  (:value x))

(defn keyword->option [k] {:label (name k) :value (subs (str k) 1)})
(defn option->keyword [x] (:value x))
