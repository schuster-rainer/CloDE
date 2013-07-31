(ns keybindings
  (:require [wpf])
  (:import [System.Windows.Input KeyEventHandler KeyEventArgs Key]
           [System.Windows WindowState WindowStyle ResizeMode]))

; (defmacro on [event handler body]
;   )
(def fullscreen {:IgnoreTaskbarOnMaximize true
                 :WindowStyle WindowStyle/None
                 :ResizeMode ResizeMode/NoResize
                 :ShowTitleBar false
                 :ShowCloseButton false
                 :ShowMinButton false
                 :ShowMaxRestoreButton false
                 :TopMoset true
                 :WindowState WindowState/Maximized})

(defmacro props-get 
  [object props]
  `(doto ~object
     ~@(map (fn [property]
              (let [property (name property)
                    getter   (str "." property)]
                `(~(symbol getter))))
            (keys props))))

(defn prop-get [keyword-prop]
  (list (symbol (str "." (name keyword-prop)))))

(defn init []
  (let [win (wpf/get-app-main-window)]
    (.add_KeyDown win (gen-delegate 
                      KeyEventHandler [sender ^KeyEventArgs e]
                      (if (= (.Key e ) Key/F11)
                        (do
                          (set! (. win IgnoreTaskbarOnMaximize) true)
                          (set! (. win WindowState) WindowState/Normal)
                          (set! (. win WindowStyle) WindowStyle/None)
                          (set! (. win ResizeMode) ResizeMode/NoResize)
                          (set! (. win ShowTitleBar) false)
                          (set! (. win ShowCloseButton) false)
                          (set! (. win ShowMinButton) false)
                          (set! (. win ShowMaxRestoreButton) false)
                          (set! (. win Topmost) true)
                          (set! (. win WindowState) WindowState/Maximized)
                          ))))))

(prn "keybindings loaded")
