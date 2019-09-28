(ns mftickets-web.domain.selector)

(defn get-selected-el-index
  "Given a list of options and a selection history, finds the index of the currently
  selected element."
  [options selection-history]
  (when (> (count options) 0)
    (if-let [selected (-> selection-history last)]
      (loop [i 0]
        (cond
          (= i (count options)) 0
          (= selected (get (vec options) i)) i
          :else                        (recur (inc i))))
      0)))

(defn select-next
  "Updates selection-history to select the next element, if any."
  [options selection-history]
  (when-let [selected-el-index (get-selected-el-index options selection-history)]
    (let [next-el (get (vec options) (inc selected-el-index) ::na)]
      (if (= next-el ::na) selection-history (conj selection-history next-el)))))

(defn select-previous
  "Updates selection-history to select the previous element, if any."
  [options selection-history]
  (when-let [selected-el-index (get-selected-el-index options selection-history)]
    (let [prev-el (get (vec options) (dec selected-el-index) ::na)]
      (if (= prev-el ::na) selection-history (conj selection-history prev-el)))))
