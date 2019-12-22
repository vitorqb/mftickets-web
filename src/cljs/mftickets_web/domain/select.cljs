(ns mftickets-web.domain.select
  "Helper namespace for handling options for the Select component."
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.domain.kw :as domain.kw]))

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

(defn keyword->option [k] {:label (domain.kw/kw->str k) :value (domain.kw/kw->str k)})
(defn option->keyword [x] (-> x :value domain.kw/str->kw))
