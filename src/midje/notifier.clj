(ns midje.notifier
  (:require [robert.hooke :refer [with-scope append]]
            [clojure.java.shell :refer [sh]]))

(defmacro notify-send [body]
  `(sh "notify-send" ~body))

(defn send-notifications []
  (let [{passes :midje-passes failures :midje-failures}
        (midje.emission.state/output-counters)]
    (notify-send
      (if (= 0 failures)
        (str "All checks (" passes ") succeeded.")
        (str failures " checks failed, " passes " succeeded")))))

(defmacro notify-me
  "Send notifications using notify-send about all successive Midje test results"
  []
  `(append midje.emission.api/fact-stream-summary (send-notifications)))
