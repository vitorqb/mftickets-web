(ns mftickets-web.domain.boolean
  (:require [clojure.string :as string]))

(defn string->boolean
  "Converts a string to a boolean"
  [x]
  (when-not (nil? x)
    (case (string/lower-case x)
      ("f" "false") false
      ("t" "true") true
      [::invalid-string x])))
