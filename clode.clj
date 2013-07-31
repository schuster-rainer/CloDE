(require '[boot])

(prn "bootstrapped CloDE")
(ns clode
  (:import 
    [System.Windows Application RoutedEventHandler ShutdownMode]
    ; [Common.Logging]
    )
  (:require 
            [wpf]
            [keybindings]
            [editor]
            [repl]
            [properties]
            [layout]
            [theming]
            [clojure.string :as string]
            ; [clojure.tools.logging :as log]
    )
  ; (:use [ClojureWpf.core] )
  (:gen-class))

; (require '[monocecil :as mc])
; (defn disassemble [t]
;   (def asm (ref (mc/dasm (type ""))))
;   (mc/add-type @asm)
;   (generate-plaintext @asm))

; (def editor_asm (editor/create-file "disassemble.cs"))
; (.AppendText editor_asm (str text))

; (defn main [& {:keys [filename] :or {filename "clodock.xaml"}}]

(defn main [& args]
  (let [app (doto (Application.)
                  ; (.set_ShutdownMode ShutdownMode/OnExplicitShutdown)
                  ; (.set_ShutdownMode ShutdownMode/OnMainWindowClose)
                  )
            win (atom (-> (first args) wpf/load-xaml))
            ]
    ; TODO: fix this later by implementing a proper mechanism
    ; for wpf/find-elem to work with dynamically loaded content
    ; like this
    ; (set! (.Content @win) (wpf/load-xaml "Module.xaml"))

    (.add_Loaded @win
                 (gen-delegate RoutedEventHandler [& args]
                               (do
                                 (editor/init)
                                 (keybindings/init) 
                                 (layout/init)
                                 (theming/init)
                                 (theming/dark)
                                 (repl/init (wpf/find-elem :repl))
                                 (editor/open-file "clode.clj") 
                                 )))
    (. app Run @win)))

(defn -main [& args]
  (apply wpf/run main 'clode args)
  )

; This code is for testing ClojureWpf.core by http://github.com/aaronc but it doesn't work
; right now as I expect it to be used. I wan't to run a repl with the WPF loop but it seems that
; the app is not in place. Haven't figured out the right way to do it
; (defn create-content []
;   (let [view (load-dev-xaml "Module.xaml")]
;     view))

; (defn init-shell-view []
;   (println "constructing MainWindow")
;   (let [view (wpf/load-xaml "Shell.xaml")]
;     (.Show view)
;     view))

; (defn -main []
;   (create-sta-thread 
;   (let [app (doto (Application.)
;                   ; (.set_ShutdownMode ShutdownMode/OnExplicitShutdown)
;                   ; (.set_ShutdownMode ShutdownMode/OnMainWindowClose)
;                   )
;             sb (app-init #'clode/create-content :init #'clode/init-shell-view)
;             ]
        
;         (.add_Loaded (:window sb
;                      (gen-delegate RoutedEventHandler [& args]
;                                    (do
;                                      (editor/init)
;                                      (keybindings/init) 
;                                      (layout/init)
;                                      (theming/init)
;                                      (theming/dark)
;                                      (repl/init (wpf/find-elem :repl))
;                                      (editor/open-file "clode.clj") 
;                                      )))
;         (. app Run (:window sb))))))

; (wpf/dev-init #'clode/create-content :init #'clode/init-shell-view)
    ; (editor/init)
    ; (println (wpf/get-app-main-window))
    ; (keybindings/init) 
    ; (layout/init)
    ; (theming/init)
    ; (theming/dark)
    ; ; (repl/init (wpf/find-elem :repl))
    ; (editor/open-file "clode.clj") 

; (def sb (wpf/dev-init #() :file-name "Shell.xaml"))

; (import '[MahApps.Metro.Controls MetroWindow])
; (def sandbox (wpf/dev-sandbox 
;                :main-window typeof(MetroWindow)))
; (def window (:window sandbox))
; ; (theming/init)
; ; (editor/init)
; ; (repl/init)
; ; (demo/light-theme)
; ; (layout/init)
; ; (keybindings/init) 
; (prn "running app")
