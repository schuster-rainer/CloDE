(ns help
  (:import [System.Diagnostics Process]))

; (import '[System.Diagnostics Process])
(def locale "en-us")
; types, namespaces and members are devied by dots "."
; constructor is named like the class: system.diagnostics.process.process
; upper-/lowercase doesn't matter
; the different class sections have appended an underscore + section to the fully qualified name
; system.diagnostics.process_methods
; system.diagnostics.process_events
; system.diagnostics.process_properties
(def msdn-uri (str "http://msdn.microsoft.com/" locale "/library/" ))

(def mdocs-online-uri (str "http://docs.go-mono.com/?link" "=" "T%3a"))
(def mdocs-offline-uri (str "mdoc://" "T%3a"))
(def google-search-uri (str "https://www.google.de/#" "hl=" locale "&q="))

(defn ProgramFilesx86 []
  (if (or (= IntPtr/Size 8)
          (not (String/IsNullOrEmpty (Environment/GetEnvironmentVariable "PROCESSOR_ARCHITEW6432"))))
    (Environment/GetEnvironmentVariable "ProgramFiles(x86)")
    (Environment/GetEnvironmentVariable "ProgramFiles")))

; WIP
; (def msdn-offline-uri (str (ProgramFilesx86) "\\Microsoft Help Viewer\\v2.0\\HlpViewer.exe"))
; (defn msdn-offline [t] 
;   (Process/Start msdn-offline-uri "/catalogName VisualStudio11 /locale en-us /launchingApp Microsoft,VisualStudio,11.0"))

; (def clojure-uri "http://clojure.github.com/clojure/" clojure.test-api.html)
; (def clj-versions {:master "branch-master/"
;                    :1.5 ""
;                    :1.4 "branch-clojure-1.4.0/"
;                    :1.3 "branch-1.3.x"})

; (defn clj-api [t]
;   (Process/Start (str clojure-uri (:master clj-versions) t "-api.html")))

(defn msdn [t] 
  (Process/Start (str msdn-uri t ".aspx")))

(defn mdocs [t] 
  (Process/Start (str mdocs-offline-uri t)))

(defn google [s]
  (Process/Start (str google-search-uri s)))
