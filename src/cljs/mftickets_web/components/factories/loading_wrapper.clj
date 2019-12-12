(ns mftickets-web.components.factories.loading-wrapper
  "Provides macros that are used as factories for loading wrappers.")

(defmacro def-loading-wrapper
  "Creates a `defn` for a loading-wrapper component.
  The created function accepts a single map that must contains an `:state` atom.
  `query-fn` is used to query the `:state` atom. If it returns true, a loading div
  is returned, else it simply returns nil."
  [name query-fn class]
  (let [state (symbol "state")]
    `(defn- ~name [{:keys [~state]}]
       {:pre [(ifn? ~query-fn) (string? ~class)]}
       (when (~query-fn @~state)
         [:div {:class [~class]} "Loading..."]))))

