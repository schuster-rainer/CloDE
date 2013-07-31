(ns rx)

(import 'System.Reactive.Linq.Observable)
(import 'System.Reactive.Observer)

(defn where [pred xs]
  (Observable/Where xs #(pred %1)))

(def filter where)

(defn select [f xs]
  (Observable/Select xs f))

(def map select)
;
; (defn from-event-pattern [^Object obs ^String event-key] 
;   (Observable/FromEventPattern obs () event-key))

(defn from-event-pattern [obs event-key] 
  (Observable/FromEventPattern ^Object obs ^String (name event-key)))

#( 
(import 'System.Reactive.Linq.Observable)
(import 'System.Reactive.Observer)
(import '[System.Windows.Input KeyEventArgs Key])
(def r (wpf/find-elem 'editor_clode))
(def key-down-events (from-event-pattern r "PreviewKeyDown"))
(def events (->> key-down-events (where #(== (.Key %1) Key/Up)) (select #(.EventArgs %1))))
(def event-handler (Observer/Create (type-args KeyEventArgs) (sys-action [KeyEventArgs] [e] (print e))))
(.Subscribe events event-handler)
)
