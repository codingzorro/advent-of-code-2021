(ns advent.day07
(:require [advent.utilities :as u]
            [clojure.string :as string]))

(def data [16,1,2,0,4,2,7,1,2,14])
; (0 1 1 2 2 2 4 7 14 16)


(defn other-crabs
  "calculate fuel needed to align all crabs with the crab at the given position"
  [sorted-crabs pos]
  (let [numbered-crabs (map #(vector %1 %2) (range) sorted-crabs)
        result         (map
                         second
                         (filter (fn [[n v]] (not= n pos)) numbered-crabs))
        ]
       result))


(println (other-crabs (sort data) 0))
