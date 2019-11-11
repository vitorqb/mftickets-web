(ns mftickets-web.domain.select
  "Helper namespace for handling options for the Select component."
  (:require [cljs.spec.alpha :as spec]))

(defn boolean->option
  "Converts a boolean into an option."
  [x]

  {:pre [(boolean? x)]
   :post [(spec/assert :select/option %)]}

  (if x {:value true :label "True"} {:value false :label "False"}))

