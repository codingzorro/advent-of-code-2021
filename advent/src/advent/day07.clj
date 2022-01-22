(ns advent.day07
(:require [advent.utilities :as u]
            [clojure.string :as string]))

(defrecord Crabs [value position])

(defn load-data [data]
  (->> data
    sort
    (frequencies ,,,)
    (map #(apply ->Crabs %) ,,,)
    vec))

(defn fuel
  "how much fuel requires the second group of crabs to move to the first group
   of crabs"
  [crab-1 crab-2]
  (let [distance (Math/abs (- (:value crab-1) (:value crab-2)))]
    (* (:position crab-2) distance)))
