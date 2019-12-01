(ns mftickets-web.specs
  (:require [cljs.spec.alpha :as spec]))

(spec/def ::state (spec/or :iswap #(satisfies? ISwap %)
                           :iatom #(satisfies? IAtom %)))
