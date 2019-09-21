(ns mftickets-web.domain.matcher
  "A ns providing string matching functionalities."
  (:require
   [clojure.string :as str]))

(defn matching-options
  "Returns a filtered of options matching a string `s`.
  `get-str-fn` defines how to extract a string from each option."
  ([options s] (matching-options options s {}))
  ([options s {:keys [get-str-fn] :or {get-str-fn identity}}]
   (let [re-str (str "(?i)" (str/replace s " " ".*"))
         re (re-pattern re-str)]
     (filter #(->> % get-str-fn (re-find re)) options))))

