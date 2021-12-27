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

(def DAYS 80)
(def sample [3 4 3 1 2])
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


