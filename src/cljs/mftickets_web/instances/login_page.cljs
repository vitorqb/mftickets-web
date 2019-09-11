(ns mftickets-web.instances.login-page
  (:require
   [mftickets-web.components.login-page :as components.login-page]))

(defn login-page-instance
  [{:keys [app-state]}]
  (let [state   (get @app-state ::state)
        reduce! #(swap! app-state update ::state %)]
    [components.login-page/login-page {:state state :reduce! reduce!}]))
