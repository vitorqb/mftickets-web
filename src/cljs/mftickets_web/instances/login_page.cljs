(ns mftickets-web.instances.login-page
  (:require
   [mftickets-web.components.login-page.core :as components.login-page]))

(defn login-page-instance
  [{:keys [app-state]}]
  (let [value   (get @app-state ::state)
        reduce! #(swap! app-state update ::state %)]
    [components.login-page/login-page {:value value :reduce! reduce!}]))
