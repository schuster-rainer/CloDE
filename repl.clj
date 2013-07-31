(ns repl
  (:import
    [ICSharpCode.Scripting CommandLineHistory]
    [ICSharpCode.SharpDevelop.Gui BeginReadOnlySectionProvider]
    [System.Windows.Input KeyEventArgs KeyEventHandler Key Keyboard]
    [Clode.Extensions ImageElementGenerator]
    [System.IO Path]
    [System.Reactive.Linq Observable]
    [Common.Logging])
  (:require 
    [wpf]
    [theming]
    [clojure.tools.logging :as log]
    [command]
    [avalonedit]))

(def control (atom nil))

(defn try-eval []
  (let [prompt (str (ns-name *ns*) "=> ")]
    (try 
      (let [cmd-text (last (command/add 
                             (avalonedit/get-entered-text @control) ))
            form (read-string cmd-text)
            ; result (eval form)
            result (eval form)
            prompt (str (ns-name *ns*) "=> ")]
        (prn cmd-text) 
        (.AppendText @control (str Environment/NewLine result Environment/NewLine prompt)))

      (catch Exception e 
        (.AppendText @control (str Environment/NewLine e Environment/NewLine prompt))  
        (prn e))

      (finally
          (avalonedit/set-entered-readonly @control)
          (avalonedit/scroll-to-end @control)))
    true))

(defn history-up []
  (prn "up")
  (avalonedit/set-current-text! @control (command/history-up))
  true)

(defn history-down []
  (prn "down")
  (avalonedit/set-current-text! @control (command/history-down))
  true)

(defn home []
  (avalonedit/set-caret-to-readonly-offset @control)
  true)

(defn on-key [s e]
  (let [handled (cond
                  (and (= (.Key e) Key/Return)
                       (or (Keyboard/IsKeyDown Key/LeftCtrl)
                           (Keyboard/IsKeyDown Key/RightCtrl))
                       ) (try-eval)
                  (and (= (.Key e) Key/Up)
                       (or (Keyboard/IsKeyDown Key/LeftCtrl)
                           (Keyboard/IsKeyDown Key/RightCtrl))
                       ) (history-up)
                  (and (= (.Key e) Key/Down)
                       (or (Keyboard/IsKeyDown Key/LeftCtrl)
                           (Keyboard/IsKeyDown Key/RightCtrl))
                       ) (history-down)
                  (= (.Key e) Key/Home) (home) 
                  :else false)]
    (prn handled)
    (.set_Handled e handled))) 

; (defn on-key [s e]
;   (cond
;     (and (= (.Key e) Key/Return)
;          (or (Keyboard/IsKeyDown Key/LeftCtrl)
;              (Keyboard/IsKeyDown Key/RightCtrl))
;          ) (try-eval s e)
;     ; (and (= (.Key e) Key/Up)
;     ;      (or (Keyboard/IsKeyDown Key/LeftCtrl)
;     ;          (Keyboard/IsKeyDown Key/RightCtrl))
;     ;      ) (history-up s e)
;     (= (.Key e) Key/Up) (history-up s e)
;     ; (and (= (.Key e) Key/Down)
;     ;      (or (Keyboard/IsKeyDown Key/LeftCtrl)
;     ;          (Keyboard/IsKeyDown Key/RightCtrl))
;     ;      ) (history-down s e)
;     (= (.Key e) Key/Down) (history-down s e)
;     )) 


(defn init [repl-elem]
  (prn "repl init")
  (reset! control repl-elem)
  (let [readOnlySection (BeginReadOnlySectionProvider.)
        prompt (str (ns-name *ns*) "=> ") ]

    (.Add (.. repl-elem TextArea TextView ElementGenerators)
          (ImageElementGenerator. (Path/Combine Environment/CurrentDirectory "images")))

    (theming/highlight repl-elem)
    (-> repl-elem
        .TextArea 
        .ReadOnlySectionProvider 
        (set! readOnlySection))

    (set! (. repl-elem Text) prompt)
    (set! (. repl-elem WordWrap) true)
    (set! (.EndOffset readOnlySection) (-> repl-elem .Document .TextLength))
    ; (let [key-events (Observable/FromEventPattern repl-elem "PreviewKeyDown")
    ;       filtered (Observable/Select
    ;                  (Observable/Where 
    ;                    (sys-func [bool] [x] true)))]
    ;   )
    (.add_PreviewKeyDown (-> repl-elem
                             .TextArea)
                         (gen-delegate KeyEventHandler [sender e]
                                       (on-key sender e)))))

; (def repl (.FindName (wpf/get-app-main-window) "repl"))
(prn "repl loaded")
