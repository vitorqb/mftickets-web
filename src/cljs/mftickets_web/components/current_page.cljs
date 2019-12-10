(ns mftickets-web.components.current-page)

(defn current-page
  "Renders the current page for the app."
  [{{:keys [login-page router-dialog header page]} :current-page/instances
    :current-page/keys [logged-in? injections]}]
  (if-not logged-in?
    login-page
    [:<> router-dialog header [page injections]]))
