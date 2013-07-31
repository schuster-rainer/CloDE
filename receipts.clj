; function arguments
(defn show [& {:keys [title name date] :as opts 
               :or {title "default title"
                    name "default name" 
                    date "default date"} }]
  {:title title :name name :date date})

(show :date (DateTime/Now) :title "some other title")
