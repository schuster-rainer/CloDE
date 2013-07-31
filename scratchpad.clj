; (ns demo
;   (:require 
;     [theming]))

; (defn demo-light-theme [& args]
;   (let [editor (.FindName (wpf/get-app-main-window) "textEditor")
;         repl  (.FindName (wpf/get-app-main-window) "repl")
;         pg (.FindName (wpf/get-app-main-window) "propertyGrid")
;         syntax-hl (load-highlighting "themes/Clojure_Light_Italian.xshd")
;         brush-converter (BrushConverter.)] 
;     (.Load editor "clode.clj")
;     (doseq [avalonedit [editor repl]]
;       (set! (.SyntaxHighlighting avalonedit) syntax-hl)
;       (set! (.Foreground avalonedit) Brushes/Black)
;       (set! (.Background avalonedit) Brushes/White))
;     (doseq [resource ["Colours" "Fonts" "Controls"]]
;       (set-mahapps-style resource))
;     (doseq [accent ["accents/italian_red" "accents/baselight"]]
;       (set-style-from-file accent))

;     (set! (.SelectedObject pg) editor)  
;     (set-style-from-file "propertygrid")

;     ; (doto curlyFoldingStrategy
;     ;   (.set_OpeningBrace \{)
;     ;   (.set_ClosingBrace \}))
;     ; (. curlyFoldingStrategy UpdateFoldings foldingManager (. editor Document))
;     ))

(ns demo)

; grab nuget from cli from http://nuget.codeplex.com/releases/view/58939 
; docs: http://docs.nuget.org/docs/reference/command-line-reference 
; and execute: nuget install Rx-Main -OutputDirectory packages
; (import 'System.Reactive.Linq.Observable)

; (def package-path "packages/")
; (dorun (map assembly-load-from 
;             [(str package-path "Rx-Core.2.1.30214.0/lib/Net40/System.Reactive.Core.dll")
;                          (str package-path "Rx-Interfaces.2.1.30214.0/lib/Net40/System.Reactive.Interfaces.dll")
;                          (str package-path "Rx-Linq.2.1.30214.0/lib/Net40/System.Reactive.Linq.dll")
;                          (str package-path "Rx-PlatformServices.2.1.30214.0/lib/Net40/System.Reactive.PlatformServices.dll")]))

; (import 'System.Reactive.Observer)

; (Observer/Create (sys-action [KeyEventArgs] [e] (print e)))

; javascript v8
; (import '[Noesis.Javascript JavascriptContext])
; (defprotocol PConsole
;   (log [^object text] "Print the text to the stdout"))

; (deftype SystemConsole [] 
;   PConsole
;   (log [text]
;     (println text)))

; (def v8 (JavascriptContext.))
; ; Setting the externals parameters of the context
; (. v8 SetParameter "console" (SystemConsole.))
; (. v8 SetParameter "message" "Hello World !")
; (. v8 SetParameter "number" 1)

; (. v8 Run "var i; for (i = 0; i < 5; i++) console.log(message + ' (' + i + ')'); number += i;")
; (println (str "number: " (. v8 GetParameter "number")))

