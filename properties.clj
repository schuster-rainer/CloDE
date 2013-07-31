(ns properties
  (:require [wpf]))

(defn show [o]
  (let [pg (.FindName (wpf/get-app-main-window) "propertyGrid")]
    (set! (.SelectedObject pg) o)))

(prn "properties loaded")
