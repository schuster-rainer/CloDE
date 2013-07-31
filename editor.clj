(ns editor
  (:import
    [Clode.Extensions BraceFoldingStrategy]
    [ICSharpCode.AvalonEdit.Folding FoldingManager]
    [AvalonDock.Layout LayoutDocument]
    [ICSharpCode.AvalonEdit TextEditor]
    [Clode.Extensions ImageElementGenerator]
    [System.IO Path]
    [System.Windows.Media FontFamily]
    )
  (:require 
    [theming]
    [bracefolding]
    ))

(defn init [& args] 
  (prn "editor init"))


(defn set-folding [editor]
  (let [foldingManager (FoldingManager/Install (.TextArea editor))
        foldingStrategy (BraceFoldingStrategy.)
        ; not ready yet
        ; foldingStrategy (bracefolding/create \( \) )
        ]
    (. foldingStrategy UpdateFoldings foldingManager (. editor Document))))


(defn open-file [filename]
  (let [win (wpf/get-app-main-window)
        dockingManager (.FindName win "dockingManager")
        docs (.FindName win "documentPane")
        doc (LayoutDocument.)
        editor (TextEditor.)
        filename-without(Path/GetFileNameWithoutExtension filename)
        component-name (str "editor_" filename-without)]
    (doto editor
      (theming/highlight-by-extension (Path/GetExtension filename))
      (.Load filename)
      (.set_Name component-name)
      (.set_FontFamily (FontFamily. "Consolas"))
      (.set_FontSize 17) 
      )
    (set-folding editor)
    (.RegisterName dockingManager component-name editor)
    (doto doc
      (.set_Content editor)
      (.set_Title filename))
    ; (theming/highlight editor)
    (-> docs .Children (.Add doc))))


(defn create-file [filename]
  (let [win (wpf/get-app-main-window)
        dockingManager (.FindName win "dockingManager")
        docs (.FindName win "documentPane")
        doc (LayoutDocument.)
        editor (TextEditor.)
        filename-without(Path/GetFileNameWithoutExtension filename)
        component-name (str "editor_" filename-without)]
    (doto editor
      (.set_Name component-name)
      (theming/highlight-by-extension (Path/GetExtension filename))
      (.set_FontFamily (FontFamily. "Consolas"))
      (.set_FontSize 17) 
      )
    (set-folding editor)
    (.RegisterName dockingManager component-name editor)
    (doto doc
      (.set_Content editor)
      (.set_Title filename))
    ; (theming/highlight editor)
    (-> docs .Children (.Add doc))
    editor))

(prn "editor loaded")
