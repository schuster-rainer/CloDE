; download nuget cli from http://nuget.codeplex.com/releases/view/58939
; run: nuget install Rx-Main -OutputDirectory packages

(dorun (map assembly-load-from 
            ["packages/Rx-Core.2.1.30214.0/lib/Net40/System.Reactive.Core.dll"
             "packages/Rx-Interfaces.2.1.30214.0/lib/Net40/System.Reactive.Interfaces.dll"
             "packages/Rx-Linq.2.1.30214.0/lib/Net40/System.Reactive.Linq.dll"
             "packages/Rx-PlatformServices.2.1.30214.0/lib/Net40/System.Reactive.PlatformServices.dll"]))

(import 'System.Reactive.Observer)
(import '[System.Windows.Input KeyEventArgs])
(println (-> Observer .Assembly .Location)) 
(let [w (Window.)
      key-down-events (from-event-pattern r "PreviewKeyDown")
      events (->> key-down-events (where #(== (.Key %1) Key/Up)) (select #(.EventArgs %1)))
      event-handler (Observer/Create (type-args KeyEventArgs) (sys-action [KeyEventArgs] [e] (print e)))
     ]
  (.Subscribe events event-handler))

