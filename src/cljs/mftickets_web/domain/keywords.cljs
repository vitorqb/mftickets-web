(ns mftickets-web.domain.keywords
  (:require [com.rpl.specter :as s]))

(defn select-ns
  "Selects the keys in a map with a common namespace."
  [m ns]
  {:pre [(symbol? ns)]}
  (s/setval [s/MAP-KEYS #(not= ns (some-> % namespace symbol))] s/NONE m))
