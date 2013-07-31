(ns avalonedit
  (:import
    [ICSharpCode.Scripting CommandLineHistory]
    [ICSharpCode.SharpDevelop.Gui BeginReadOnlySectionProvider]
    [System.Windows.Input KeyEventHandler Key Keyboard])
  (:require 
    [clojure.tools.logging :as log]))


(defn get-entered-text [edit]
  (let [offset (-> edit .TextArea .ReadOnlySectionProvider .EndOffset)
        length (-> edit .Document .TextLength)]
  (-> edit .Document (.GetText offset (- length offset)))))

(defn set-entered-readonly [edit]
  (set! (-> edit .TextArea .ReadOnlySectionProvider .EndOffset) (-> edit .Document .TextLength)))

(defn set-current-text! [edit text]
  (let [offset (-> edit .TextArea .ReadOnlySectionProvider .EndOffset)
        end (-> edit .Document .TextLength)
        lenght (- end offset)]
  (-> edit .Document (.Replace offset lenght text))))

(defn scroll-to-end [edit]
  (.ScrollToEnd edit))

(defn set-caret-to-readonly-offset [edit]
  (let [offset (-> edit .TextArea .ReadOnlySectionProvider .EndOffset)]
    (.set_CaretOffset edit offset)))

