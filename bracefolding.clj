(ns bracefolding
  (:import [ICSharpCode.AvalonEdit.Folding AbstractFoldingStrategy NewFolding]
           [System.Collections.Generic.IEnumerable]))

(defn create [opening-brace closing-brace]
  (proxy [AbstractFoldingStrategy] []
    (CreateNewFoldings [doc firstErrorOffset]
       ; (set! firstErrorOffset -1)
        (let [ text (map-indexed (fn [idx itm] [idx itm]) (.Text doc))]
          ((NewFolding. 1 5))))))


; (defn create [^char opening-brace closing-brace]
;   (proxy [AbstractFoldingStrategy] []
;     (CreateNewFoldings [doc firstErrorOffset]
;        ; (reset! firstErrorOffset -1)
;         (let [ text (map-indexed (fn [idx itm] [idx itm]) (.Text doc))
;              opening-braces (filter #(== %1 opening-brace) text)
;              closing-braces (reverse (filter #(== %1 closing-brace) text))]
;           (prn text)
;         (map (fn [[open-offset _ ] [close-offset _ ]] 
;                 (NewFolding. open-offset (+ 1 close-offset)))
;               opening-braces closing-braces)
;           (prn "done")))))



   ; (:gen-class
   ;  :extends AbstractFoldingStrategy
   ;  :methods [
   ;          [CreateNewFoldings [^ITextSource document] |^System.Collections.Generic.IEnumerable`1[NewFolding]|]
   ;          [CreateNewFoldings [^TextDocument document (by-ref ^int firstErrorOffset)] |^System.Collections.Generic.IEnumerable`1[NewFolding]|]
   ;  ])

; (defn create [opening-brace closing-brace]
;   (proxy [AbstractFoldingStrategy] []
;     (CreateNewFoldings 
;       ([doc] [])
;       ([doc firstErrorOffset] 
;        (reset! firstErrorOffset -1) 
;        (.CreateNewFoldings doc)))))


; (let [new-foldings []
;       opening-offsets []
;       closing-offsets []
;       opening "("
;       closing ")" 
;       last-new-line-offset]
;   (for [x (range 0 (.TextLength doc))
;         :let [c (.GetCharAt doc x)]]
;     (cond 
;       (== c opening) (conj opening-offsets i))
;       (and (> (count opening-offsets) 0)
;            (== c opening)) (let [ opening-offset (pop opening-offsets)]
;                              (if (< opening-offset last-new-line-offset)
;                                (NewFolding. opening-offset (+ x 1)))
;                              ))))



