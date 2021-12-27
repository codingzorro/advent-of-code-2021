(ns advent.day06
(:require [advent.utilities :as u]
            [clojure.string :as string]))

(def LIFETIME 7)


(defn birth-dates
  "find the 'date' when a new fish are born depending on the parent's counter"
  [counter days-to-go]
  (take-while #(<= % days-to-go)
  (map #(+ (inc counter) %)
       (take-while #(<= % days-to-go) (iterate #(+ % 7) 0)))))


(defn fish-maker [counter days-to-go]
  (let [birth-dates (birth-dates counter days-to-go)]
    (map #(vector 5 (- days-to-go %1 3)) birth-dates)))

  ;(map #(vector 5 (- %2 %1)) (map #(+ 3 %) tage) (repeat days-to-go)))


; (map #(apply birth-dates %) %(fishmaker tage 20))

(def result "6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8")
(count (string/split result #","))

(def DAYS 80)
(def sample [3 4 3 1 2])
(def data (map #(vector %1 %2) sample (repeat DAYS)))



(println
(count
(loop [all-fish data result []]
  (if (empty? all-fish)
    result
    (do
;     (println "**" (into (rest all-fish) (apply fish-maker (first all-fish))))
;     (println result)
      (recur (into (rest all-fish) (apply fish-maker (first all-fish)))
             (conj result (first all-fish))))))
)
)


