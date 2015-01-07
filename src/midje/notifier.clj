(ns midje.notifier
  (:require [robert.hooke :refer [with-scope append]]
            [clojure.java.shell :refer [sh]]))

(defmacro exists? [program] `(= 0 (:exit (sh "which" ~program))))

(defonce notification-type
  (cond
    (exists? "notify-send")       :notify-send
    (exists? "terminal-notifier") :terminal-notifier))

(defmacro notify-send [title body]
  (case notification-type
    :notify-send       `(sh "notify-send" ~title ~body)
    :terminal-notifier `(sh ":terminal-notifier" "-message" ~body "-title" ~title)))

(defn send-notifications []
  (let [{passes :midje-passes failures :midje-failures}
        (midje.emission.state/output-counters)]
    (notify-send
      "Tests complete"
      (if (= 0 failures)
        (str "All checks (" passes ") succeeded.")
        (str failures " checks failed, " passes " succeeded")))))

(defmacro notify-me
  "Send notifications using notify-send about all successive Midje test results"
  []
  `(append midje.emission.api/fact-stream-summary (send-notifications)))
