(ns mftickets-web.domain.sequences)

(defn move-back
  "Reorders seq so that the item satisfying pred is one element before.
  (= [2 1 3] (move-back #{2} [1 2 3]))
  (= [1 2 3] (move-back #{4} [1 2 3]))
  (= [1 2 3] (move-back #{1} [1 2 3]))"
  [pred coll]
  (loop [done [] [el & todo] coll]
    (cond
      (pred el)
      (concat (butlast done) [el] (some-> done last vector) todo)

      (empty? todo)
      (concat done [el])

      :else
      (recur (concat done [el]) todo))))

(defn move-forward
  "Reorders seq so that the item satisfying pred is one element after.
  (= [1 3 2] (move-forward #{2} [1 2 3]))
  (= [1 2 3] (move-forward #{4} [1 2 3]))
  (= [2 1 3] (move-forward #{1} [1 2 3]))"
  [pred coll]
  (loop [done [] [el & todo] coll]
    (cond
      (pred el)
      (concat done (some-> todo first vector) [el] (rest todo))

      (empty? todo)
      (concat done [el])

      :else
      (recur (concat done [el]) todo))))
