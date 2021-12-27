(ns advent.day06
(:require [advent.utilities :as u]
            [clojure.string :as string]))

(def LIFETIME 7)


(defn birth-dates
  "find the 'date' when a new fish are born depending on the parent's counter"
  [counter days-to-go]
  (let [check #(<= % days-to-go)]
    (take-while check
               (map #(+ (inc counter) %) (take-while check
                                                     (range 0 days-to-go 7))))))

(defn fish-maker [counter days-to-go]
  (let [birth-dates (birth-dates counter days-to-go)]
    (pmap #(vector 5 (- days-to-go %1 3)) birth-dates)))

(def DAYS 80)
;(def sample [3 4 3 1 2])
(def sample [5,1,5,3,2,2,3,1,1,4,2,4,1,2,1,4,1,1,5,3,5,1,5,3,1,2,4,4,1,1,3,1,1,3,1,1,5,1,5,4,5,4,5,1,3,2,4,3,5,3,5,4,3,1,4,3,1,1,1,4,5,1,1,1,2,1,2,1,1,4,1,4,1,1,3,3,2,2,4,2,1,1,5,3,1,3,1,1,4,3,3,3,1,5,2,3,1,3,1,5,2,2,1,2,1,1,1,3,4,1,1,1,5,4,1,1,1,4,4,2,1,5,4,3,1,2,5,1,1,1,1,2,1,5,5,1,1,1,1,3,1,4,1,3,1,5,1,1,1,5,5,1,4,5,4,5,4,3,3,1,3,1,1,5,5,5,5,1,2,5,4,1,1,1,2,2,1,3,1,1,2,4,2,2,2,1,1,2,2,1,5,2,1,1,2,1,3,1,3,2,2,4,3,1,2,4,5,2,1,4,5,4,2,1,1,1,5,4,1,1,4,1,4,3,1,2,5,2,4,1,1,5,1,5,4,1,1,4,1,1,5,5,1,5,4,2,5,2,5,4,1,1,4,1,2,4,1,2,2,2,1,1,1,5,5,1,2,5,1,3,4,1,1,1,1,5,3,4,1,1,2,1,1,3,5,5,2,3,5,1,1,1,5,4,3,4,2,2,1,3])
(def data (map #(vector %1 %2) sample (repeat DAYS)))



(println
(count
(loop [all-fish data result []]
  (if (empty? all-fish)
    result
    (recur (into (rest all-fish) (apply fish-maker (first all-fish)))
           (conj result (first all-fish)))))
)
)


