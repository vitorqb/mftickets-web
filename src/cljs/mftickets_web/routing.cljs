(ns mftickets-web.routing
  (:require [reitit.core]
            [reitit.frontend]))

(defn route-match->component
  [route-match]
  {:pre [(instance? reitit.core/Match route-match)]}
  (some-> route-match :data :component))

(defn router->user-routing-opts
  "Takes a reitit router and parses it into a list of routes that a user can select and
  navigate to."
  [router]
  {:pre [(satisfies? reitit.core/Router router)]}
  (for [[_ {label :label name :name} _] (reitit.core/compiled-routes router)]
    {:label label :href (->> name (reitit.frontend/match-by-name router) :path)}))
