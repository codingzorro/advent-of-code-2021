(ns advent.day06
(:require [advent.utilities :as u]
            [clojure.string :as string]))

(def LIFETIME 7)

(def bd-lookup (atom {}))
(def fm-lookup (atom {}))

(defn birth-dates
  "find the 'date' when a new fish are born depending on the parent's counter"
  [^Integer counter ^Integer days-to-go]
  (let [check #(<= % days-to-go)]
    (take-while check
               (map #(+ (inc counter) %) (take-while check
                                                     (range 0 days-to-go 7))))))

(defn bd [^Integer counter ^Integer days-to-go]
  (or
    (@bd-lookup [counter days-to-go])
    (let [new-value (birth-dates counter days-to-go)]
          (swap! bd-lookup assoc [counter days-to-go] new-value)
          new-value)))


(defn fish-maker [[^Integer counter ^Integer days-to-go]]
  (let [birth-dates (or
                      (@bd-lookup [counter days-to-go])
                      (let [new-value (birth-dates counter days-to-go)]
                            (swap! bd-lookup assoc [counter days-to-go] new-value)
                            new-value))
        new-days-to-go (- days-to-go 3)
        values (pmap #(- new-days-to-go %) birth-dates)]
    (vec (map #(vector %1 %2) (repeat 5) values))))

(def DAYS 80)
;(def sample [3 4 3 1 2])
(def sample [5,1,5,3,2,2,3,1,1,4,2,4,1,2,1,4,1,1,5,3,5,1,5,3,1,2,4,4,1,1,3,1,1,3,1,1,5,1,5,4,5,4,5,1,3,2,4,3,5,3,5,4,3,1,4,3,1,1,1,4,5,1,1,1,2,1,2,1,1,4,1,4,1,1,3,3,2,2,4,2,1,1,5,3,1,3,1,1,4,3,3,3,1,5,2,3,1,3,1,5,2,2,1,2,1,1,1,3,4,1,1,1,5,4,1,1,1,4,4,2,1,5,4,3,1,2,5,1,1,1,1,2,1,5,5,1,1,1,1,3,1,4,1,3,1,5,1,1,1,5,5,1,4,5,4,5,4,3,3,1,3,1,1,5,5,5,5,1,2,5,4,1,1,1,2,2,1,3,1,1,2,4,2,2,2,1,1,2,2,1,5,2,1,1,2,1,3,1,3,2,2,4,3,1,2,4,5,2,1,4,5,4,2,1,1,1,5,4,1,1,4,1,4,3,1,2,5,2,4,1,1,5,1,5,4,1,1,4,1,1,5,5,1,5,4,2,5,2,5,4,1,1,4,1,2,4,1,2,2,2,1,1,1,5,5,1,2,5,1,3,4,1,1,1,1,5,3,4,1,1,2,1,1,3,5,5,2,3,5,1,1,1,5,4,3,4,2,2,1,3])
(def data (vec (map #(vector %1 %2) sample (repeat DAYS))))

(defn to-frequencies [a-vector]
  (frequencies (sort a-vector)))


(defn multicall [f [kv n]]
  (let [result (f kv)]
    (map #(vector % n) result )))

(defn how-many [frequency-list]
  (->> frequency-list
    (map second ,,,)
    (reduce + 0)))

; (println
;   (->> data
;     (to-frequencies ,,,)
;     (mapcat #(multicall fish-maker %) ,,,)
;     (reduce (fn [d [k n]] (update d [k n] (fnil inc 0))) {} ,,,)    ; EQUIVALENT TO PYTHON'S DEFAULT DICT USED AS COUNTER
;     )
; )


(defn consolidate [m [[counter days] number]]
  (if-let [current-number (m [counter days])]
    (update m [counter days] + number)
    (assoc m [counter days] number)))

(defn -main [& args]
  (time
    (println
      (let [first-generation (to-frequencies data)]
        (loop [current-generation first-generation result (how-many first-generation)]
          (if (empty? current-generation)
            result
            (let [next-generation (->> current-generation
                                    (mapcat #(multicall fish-maker %) ,,,)
                                    (reduce consolidate {} ,,,)
                                    )]
              (recur next-generation (+ result (how-many next-generation))))))))))


(println (-main))
