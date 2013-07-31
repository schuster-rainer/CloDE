; bootstrapping dependencies to load first
(ns boot
  (:require [dependencies :as d]))

(assembly-load-with-partial-name "PresentationFramework")
(assembly-load-with-partial-name "PresentationCore")
(assembly-load-with-partial-name "WindowsBase")
(assembly-load-with-partial-name "System")
(assembly-load-with-partial-name "System.Xml")
(assembly-load-with-partial-name "System.Xaml")
;(assembly-load-with-partial-name "System.Reactive")
; (assembly-load-with-partial-name "Awesomium.Windows.Controls")


(def expression-sdk "C:\\Program Files (x86)\\Microsoft SDKs\\Expression\\Blend\\.NETFramework\\v4.0\\Libraries\\")
; (def awesomium-sdk"C:\\Program Files (x86)\\Awesomium Technologies LLC\\Awesomium SDK\\")

(def references 
  [(str expression-sdk "Microsoft.Expression.Interactions.dll")
   (str expression-sdk "System.Windows.Interactivity.dll")
   ; "ClojureWpf/ClojureWpf/bin/Debug/ClojureWpf.dll"
   ; (str awesomium-sdk "1.7.0.5/wrappers/Awesomium.NET/Assemblies/Awesomium.Windows.Controls.dll")
   "Clode.Extensions/Clode.Extensions/bin/Debug/Clode.Extensions.dll"
   (str d/*package-path* "ILSpy/Mono.Cecil.dll")
   (str d/*package-path* "ILSpy/ICSharpCode.Decompiler.dll")
   ; TODO: upgrade to new versions and use dependencies.clj or even better
   ; nLeiningen with project.clj
   (str d/*package-path* "log4net.2.0.0/lib/net40-full/log4net.dll") 
   (str d/*package-path* "Common.Logging.2.1.2/lib/net40/Common.Logging.dll") 
   (str d/*package-path* "MahApps.Metro.0.10.0.1/lib/net40/MahApps.Metro.dll") 
   (str d/*package-path* "AvalonEdit.4.2.0.8783/lib/Net40/ICSharpCode.AvalonEdit.dll")
   (str d/*package-path* "Extended.Wpf.Toolkit.1.9.0/lib/net40/Xceed.Wpf.Toolkit.dll")
   (str d/*package-path* "Extended.Wpf.Toolkit.1.9.0/lib/net40/Xceed.Wpf.DataGrid.dll")
   (str d/*package-path* "AvalonDock.2.0.1746/lib/net40/AvalonDock.dll")
   (str d/*package-path* "AvalonDock.2.0.1746/lib/net40/AvalonDock.Themes.Metro.dll")
   (str d/*package-path* "Rx-Core.2.1.30214.0/lib/Net40/System.Reactive.Core.dll")
   (str d/*package-path* "Rx-Interfaces.2.1.30214.0/lib/Net40/System.Reactive.Interfaces.dll")
   (str d/*package-path* "Rx-Linq.2.1.30214.0/lib/Net40/System.Reactive.Linq.dll")
   ; (str d/*package-path* "Rx-PlatformServices.2.1.30214.0/lib/Net40/System.Reactive.PlatformServices.dll")
   ; (str d/*package-path* "Rx-Experimental.2.1.30214.0/lib/Net40/System.Reactive.Experimental.dll")
   ; (str d/*package-path* "NAudio.1.6/lib/net20/NAudio.dll")

   ; (str d/*package-path* "Noesis.Javascript.0.7.1.0/lib/net40/amd64/Noesis.Javascript.dll")
   ])

(dorun (map assembly-load-from references))

; (import '[Common.Logging])
; (require '[clojure.tools.logging :as log])
(prn "added and loaded references")
