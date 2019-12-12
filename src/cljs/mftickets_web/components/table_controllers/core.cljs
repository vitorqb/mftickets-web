(ns mftickets-web.components.table-controllers.core
  (:require [mftickets-web.components.table-controllers.page-updown :as c.table-controllers.page-updown]
            [mftickets-web.components.table-controllers.page-size-selector :as c.table-controllers.page-size-selector]
            [mftickets-web.state :as state]
            [mftickets-web.components.table-controllers.css :as css]
            [mftickets-web.domain.keywords :as domain.keywords]))

;; Globals
(defn- select-shared-keys
  "Select keys from props that are shared with children components."
  [props]
  (merge
   (domain.keywords/select-ns props 'table-controllers.messages)
   (domain.keywords/select-ns props 'table-controllers)))

;; Components
(defn table-controller
  "A controller, allowing the user to read and update configuration for a table.
  The controller is agnostic from the table implementation, and only connects the
  user actions to the messages that must be handled higher in the hierarchy."
  [{:keys [state] :as props}]
  (let [props* (select-shared-keys props)]
    [:div {:class css/base-class}
     [c.table-controllers.page-updown/page-updown props*]
     (let [state* (state/->FocusedAtom state [:page-size-selector])
           props** (assoc props* :state state*)]
       [c.table-controllers.page-size-selector/page-size-selector props**])]))
