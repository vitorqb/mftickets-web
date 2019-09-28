(ns mftickets-web.components.life-prober
  (:require
   [mftickets-web.components.life-prober.queries :as queries]
   [mftickets-web.components.life-prober.handlers :as handlers]
   [mftickets-web.events :as events]
   [mftickets-web.events.protocols :as events.protocols]))

(def base-class "life-prober")
(def label-class (str base-class "__label"))
(def button-class (str base-class "__button"))
(def status-displayer-wrapper-class (str base-class "__status-displayer-wrapper"))
(def status-displayer-class (str base-class "__status-displayer"))
(def status-displayer-class-live-modifier (str status-displayer-class "--live"))
(def status-displayer-class-dead-modifier (str status-displayer-class "--dead"))
(def status-displayer-class-unknown-modifier (str status-displayer-class "--unknown"))

(defn get-status-displayer-classes
  [{:keys [state]}]
  (conj
   [status-displayer-class]
   (case (queries/status @state)
     :live status-displayer-class-live-modifier
     :dead status-displayer-class-dead-modifier
     :unknown status-displayer-class-unknown-modifier)))

(defn status-displayer
  "Displays the current status."
  [props]
  [:div {:class [status-displayer-wrapper-class]}
   [:span {:class (get-status-displayer-classes props)} "."]])

(defn ping-button
  "A button for the action of checking if we are alive."
  [props]
  [:button
   {:class button-class
    :on-click #(->> (handlers/ping props) (events/react! props))}
   "Probe Life"])

(defn life-prober
  "A small component used to ping the server and check we are alive."
  [props]
  [:div {:class [base-class]}
   [ping-button props]
   [status-displayer props]])
