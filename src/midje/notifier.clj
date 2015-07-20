(ns midje.notifier
  (:require [robert.hooke :refer [with-scope append]]
            [clojure.java.shell :refer [sh]]))

(defn exists? [program]
  (->> program
       (sh "which")
       :exit
       (= 0)))

(defonce notification-type
  (cond
    (exists? "notify-send")       :notify-send
    (exists? "terminal-notifier") :terminal-notifier))

(defmulti send-notification (constantly notification-type))

(defmethod send-notification :notify-send
  [title body & {:keys [pass?]}]
  (sh "notify-send"
      "-h" (str "string:bgcolor:"(if pass? "#008800" "#ff4444"))
      title body))

(defmethod send-notification :terminal-notifier
  [title body & {:keys [pass?]}]
  ;; FIXME pass? is ignored, some iPerson please add some visual indicator of pass/fail
  (sh "terminal-notifier" "-message" body "-title" title))

(defmethod send-notification :default
  [title body & {:keys [pass?]}]
  (println "Unable to send notification, do you have either terminal-notifier or notify-send on your $PATH?")
  (println title body pass?))

(defn send-notifications []
  (let [{passes :midje-passes failures :midje-failures}
        (midje.emission.state/output-counters)]
    (let [passed (= 0 failures)]
      (send-notification
       "Tests complete"
       (if passed
         (str "All checks (" passes ") succeeded.")
         (str failures " checks failed, " passes " succeeded"))
       :pass? passed))))

(defn notify-me
  "Send notifications using notify-send or terminal-notifier after each Midje test run"
  []
  (append midje.emission.api/fact-stream-summary (send-notifications)))
