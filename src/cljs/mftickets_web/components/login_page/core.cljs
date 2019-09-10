(ns mftickets-web.components.login-page.core)

(defn increase-count [reduce!]
  (fn [_]
    (reduce! #(update % :count inc))))

(defn decrease-count [reduce!]
  (fn [_]
    (reduce! #(update % :count dec))))

(defn reset-count [reduce!]
  (fn [_]
    (reduce! #(assoc % :count 0))))

(defn change-increase-by-input [reduce!]
  (fn [e]
    (let [value (-> e .-target .-value)]
      (reduce! #(assoc-in % [:inputs :increase-by :value] value)))))

(defn increase-by [reduce!]
  (fn [_]
    (reduce!
     (fn [value]
       (let [increase-by (-> value :inputs :increase-by :value int)]
         (update value :count + increase-by))))))

(defn login-page
  "Initial page used for login."
  [{:keys [value reduce!]}]
  [:div
   "LoginPage!"
   [:p (-> value :count (or 0))]
   [:button {:on-click (increase-count reduce!)} "+"]
   [:button {:on-click (decrease-count reduce!)} "-"]
   [:button {:on-click (reset-count reduce!)} "reset"]
   [:div
    [:input {:value (or (-> value :inputs :increase-by :value) 0)
             :on-change (change-increase-by-input reduce!)}]
    [:button {:on-click (increase-by reduce!)} "increase!"]]])
