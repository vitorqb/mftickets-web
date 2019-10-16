(ns mftickets-web.events.specs
  (:require [cljs.spec.alpha :as spec]
            [mftickets-web.events.protocols :as events.protocols]))

(spec/def ::state
  (spec/nilable (spec/and #(satisfies? ISwap %) #(satisfies? IDeref %))))

(spec/def ::parent-props
  (spec/keys :opt-un [::state ::parent-props]))

(spec/def ::event
  #(satisfies? events.protocols/PEvent %))
