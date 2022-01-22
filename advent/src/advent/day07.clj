(ns advent.day07
(:require [advent.utilities :as u]
            [clojure.string :as string]))

(defrecord Crabs [position number])

(defn load-data
  "return a SORTED by position list of `Crabs` instances"
  [data]
  (->> data
    sort
    (frequencies ,,,)
    (map #(apply ->Crabs %) ,,,)
    vec))

(defn fuel
  "how much fuel is required to move the crabs on a given position to
   another position"
  [position crabs]
  (let [distance (Math/abs (- position (:position crabs)))]
    (* (:number crabs) distance)))

(defn total-fuel
  "how much fuel is required to move ALL crabs (regardless of their
   current position) to another position"
  [position all-crabs]
  (reduce + (map #(fuel position %) (load-data all-crabs))))

(defn split
  "Parameter `e` is an element of `collection`.  Return a vector
   with two elements: `e` and all elements other than `e`"
  [e collection]
  (vector e (filter #(not= e %) collection)))

(defn least-fuel
  [data]
  (let [all-crabs (load-data data)
        candidates (range (:position (first all-crabs))
                          (inc (:position (last all-crabs))))]
    (map #(total-fuel % data) candidates)))


