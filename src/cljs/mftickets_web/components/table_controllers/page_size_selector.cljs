(ns mftickets-web.components.table-controllers.page-size-selector
  (:require [mftickets-web.components.input :as components.input]
            [mftickets-web.components.form :as components.form]))

(defn page-size-selector
  "A input-like object that allows the user to select the size of the table page"
  [{:keys [state]
    :table-controllers/keys [current-page-size]
    :table-controllers.messages/keys [on-page-size-change]}]
  [:div {}
   [components.form/form {:on-submit #(-> @state :value on-page-size-change)
                          :button-style :none}
    [components.input/input
     {:key ::page-size-selector
      :input/label "Page size"
      :input/value (:value @state current-page-size)
      :input.messages/on-change #(swap! state assoc :value %)}]]])
