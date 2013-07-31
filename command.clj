(ns command)

(def history-index (ref 0))
(def history (ref []))

; TODO: fix last/first element bug
; (defn history-up []
;   (if (< @command/history-index
;          (count @command/history))
;     (if-let [ command (get @command/history
;                            (dosync (alter history-index inc)))]
;       ;TODO: send message history up
;       command)
;     ""))

(defn history-up []
  (if-let [ command (get @command/history (inc @command/history-index))]
    ;TODO: send message history up
    (dosync
      (alter history-index inc)
      command)
    ""))

(defn history-down []
  (if-let [ command (get @command/history (dec @command/history-index))]
    ;TODO: send message history down
    (dosync
      (alter history-index dec)
      command)
    ""))

(defn add [text]
  (dosync
    (alter history-index inc)
    (alter history conj text)))

(defn show-history []
  @history)
