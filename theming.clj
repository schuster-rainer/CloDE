(ns theming
  (:require [wpf])
  (:import 
    [System.IO File]
    [System.Xml XmlReader]
    [System.Windows Application ResourceDictionary] 
    [System.Windows.Media Brushes SolidColorBrush ColorConverter BrushConverter]    
    [ICSharpCode.AvalonEdit.Highlighting HighlightingManager IHighlightingDefinition]
    [ICSharpCode.AvalonEdit.Highlighting.Xshd HighlightingLoader]
    [Clode.Extensions Highlighting]
    ))

(defn load-highlighting [filename]
  (-> filename
      File/OpenText
      XmlReader/Create 
      (HighlightingLoader/Load HighlightingManager/Instance)))

(defn set-highlighting [textEditor filename]
  (->> filename
       load-highlighting
       (set! (.SyntaxHighlighting textEditor))))

(defn set-mahapps-style [style]
  (-> (str "pack://application:,,,/MahApps.Metro;component/Styles/" style ".xaml" )
      wpf/load-resource
      wpf/merge-window-resource))

(defn set-style-from-file [style]
  (-> (str "styles/" style ".xaml" )
      wpf/load-xaml
      wpf/merge-window-resource))

(defn color [color-string] 
  (ColorConverter/ConvertFromString color-string))

(def brush-converter (BrushConverter.))

(defn brush [color-string]
  (.ConvertFromString brush-converter color-string))

; baseaccent for dark
(def darkaccent 
  {:WhiteColor  (color "#FF252525")
   :BlackColor  (color "#FFFFFFFF")
   :NormalForgroundColour (color "#FFFFFFFF")
   :HoverForegroundColor (color "#FF000000")
   :Gray5 (color "#FF7e7e7e")
   :Gray7 (color "#FF2f2f2f")
   :Gray8 (color "#FF454545")
   :GrayNormal (color "#FF7D7D7D")
   :GrayHover (color "#FFAAAAAA")})

;color accent
(def purpleaccent
  {:HighlightDarkColor (color "#FFb400ff")
   :HighlightLightColor (color "#FFb400ff")
   :AccentColor (color "#CCb400ff")
   :AccentColor2 (color "#99b400ff")
   :AccentColor3 (color "#66b400ff")
   :AccentColor4 (color "#33b400ff")
   ; linear gradient brush is missing atm
   :AccentColorBrush  (brush "#CCb400ff")})

(def purple-haze-syntax-theme
  {:gray (color "#FF808080")
   :blue (color "#FF88BDF8")
   :purple (color "#FFA078A6")
   :yellow (color "#FFF2DB8C")
   :green (color "#FF84D2AF")
   :red (color "#FFD85050")})

; (def syntax-purple-haze
;   {:Comment (color ) })

(defn set-style [colors]
  (let [rc (ResourceDictionary.)]
    (doseq [[k v] colors]
      (.Add rc (name k) v))
    (wpf/merge-window-resource rc)
    rc))

; (defn set-mahapps-accent [accent]
;   (-> (str "pack://application:,,,/MahApps.Metro;component/Styles/Accents/" accent ".xaml" )
;       wpf/load-resource
;       wpf/merge-window-resource))

(defn highlight [editor]
  (set! (.SyntaxHighlighting editor)
        (.GetDefinitionByExtension HighlightingManager/Instance ".clj")) 
  (set! (.Foreground editor) Brushes/White)
  (set! (.Background editor) (.ConvertFromString brush-converter "#FF252525")))

(defn highlight-by-extension [editor extension] 
  (set! (.SyntaxHighlighting editor)
        (.GetDefinitionByExtension HighlightingManager/Instance extension)) 
  (set! (.Foreground editor) Brushes/White)
  (set! (.Background editor) (.ConvertFromString brush-converter "#FF252525")))

(defn register [^"System.String" filetype
                ^IHighlightingDefinition hl
                ^"System.String[]" definitions]
    (HighlightingManager/Instance (.RegisterHighlighting filetype definitions hl))  
  )

(defn register-syntax [schema]
  (let [hl (load-highlighting (str "themes/" schema ".xshd"))
        definitions (into-array String [".clj"])]
    ; (HighlightingManager/Instance (.RegisterHighlighting "Clojure" definitions hl)
    ; TODO: How To call a function that's overloaded by type not arity?
  (Highlighting/Register "Clojure" definitions hl)))

(defn init []
  ; (register-syntax "Clojure_Light_Italian"))
  (register-syntax "Clojure_Dark"))

(defn dark [& args]
    ; atm. use the base resources from the assembly
    (doseq [resource ["Colours" "Fonts" "Controls"]]
      (theming/set-mahapps-style resource))
    ; work in progress
    ; (doseq [accent [darkaccent purpleaccent]]
    ;   (set-style accent))
    (doseq [accent ["accents/purplehaze_purple" "accents/basedark"]]
      (theming/set-style-from-file accent))
    (theming/set-style-from-file "propertygrid"))

(defn light [& args]
   ; atm. use the base resources from the assembly
    (doseq [resource ["Colours" "Fonts" "Controls"]]
      (theming/set-mahapps-style resource))
    ; work in progress
    ; (doseq [accent [darkaccent purpleaccent]]
    ;   (set-style accent))
    (doseq [accent ["accents/italian_red" "accents/baselight"]]
      (theming/set-style-from-file accent))

    (theming/set-style-from-file "propertygrid"))

(prn "theming loaded")
