(ns mftickets-web.domain.kw
  (:require  [cljs.test :refer-macros [is are deftest testing use-fixtures async]]))

(defn kw->str
  "Transforms a keyword into a string and strips out the leading `:`. Therefore we
  can convert back to a keyword using `keyword`."
  [kw]
  (-> kw str (subs 1)))

(def str->kw keyword)
