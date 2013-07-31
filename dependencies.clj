(ns dependencies
  (:import 
    [System.Diagnostics Process]
    [System.Net WebClient]
    [System Uri]))

(def ^:dynamic *package-path* "packages/")

(defn install [[package version & args]]
  (let [nuget "NuGet.exe"
        arguments (str "install " package 
                       " -OutputDirectory " *package-path*
                       " -Version " version)]
    (prn nuget arguments)
    (when-let [process (Process/Start nuget arguments)]
      (. process WaitForExit)
      )))

(defn -main [& args]
  (let [packages [["log4net" "2.0.0" ["lib/net40-full/log4net.dll"]]
                  ["Common.Logging" "2.1.2" ["lib/net40/Common.Logging.dll"]]
                  ["MahApps.Metro" "0.10.0.1" ["lib/net40/MahApps.Metro.dll"]]
                  ["AvalonDock" "2.0.1746" ["lib/net40/AvalonDock.dll"
                                            "lib/net40/AvalonDock.Themes.Metro.dll"]]
                  ["AvalonEdit" "4.2.0.8783" ["lib/Net40/ICSharpCode.AvalonEdit.dll"]]
                  ["Extended.Wpf.Toolkit" "1.9.0" ["lib/net40/Xceed.Wpf.Toolkit.dll"
                                                   "lib/net40/Xceed.Wpf.DataGrid.dll"]]
                  ["Rx-Core" "2.1.30214.0" ["lib/Net40/System.Reactive.Core.dll"]]
                  ["Rx-Interfaces" "2.1.30214.0" ["lib/Net40/System.Reactive.Interfaces.dell"]]
                  ["Rx-Linq" "2.1.30214.0" ["lib/Net40/System.Reactive.Linq"]]
                 ;optional for later use
                  ; "Noesis.Javascript"
                  ; "NAudio"
                  ; "Rx-Interfaces"
                  ; "Rx-Core"
                  ; "Rx-Linq"
                  ; "Rx-PlatformServices"
                  ; "Rx-Experimental"
                  ; "Rx-Xaml"
                  ; "Rx-Wpf"
                  ; "VisualRx"
            ]]
    ; (. (WebClient.) DownloadFile (Uri. "http://nuget.org/nuget.exe") "NuGet.exe")
    (dorun (map install packages))))
