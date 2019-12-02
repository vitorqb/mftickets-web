(ns mftickets-web.components.header
  (:require
   [mftickets-web.components.life-prober :as components.life-prober]
   [mftickets-web.state :as state]))

(def base-class "header")

(defn- life-prober
  "Wrapper for a life prober."
  [{:keys [state reduce!] :as props}]
  [components.life-prober/life-prober
   (assoc props :state (state/->FocusedAtom state [::life-prober]))])

(defn- router-btn
  "A button used to display the router dialog."
  [{:header.messages/keys [display-router]}]
  {:pre [(ifn? display-router)]}
  [:button {:on-click display-router}
   "Router"])

(defn- refresh-app-metadata-btn
  "A button to refresh the app metadata."
  [{:header.messages/keys [refresh-app-metadata]}]
  {:pre [(fn? refresh-app-metadata)]}
  [:button {:on-click refresh-app-metadata}
   "Refresh!"])

(defn header
  "The header of the app."
  [props]
  [:header {:class [base-class]}
   [life-prober props]
   [router-btn props]
   [refresh-app-metadata-btn props]])
