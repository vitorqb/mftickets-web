(ns mftickets-web.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [mftickets-web.core-test]))

(doo-tests 'mftickets-web.core-test)
