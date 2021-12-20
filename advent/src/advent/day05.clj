(ns advent.day04
  (:require [advent.utilities :as u]
            [clojure.string :as string]))


(def lines (string/split (slurp "resources/day05-test-input.txt") #"\n"))

(defn line-to-line
  "transforms a file line into a hydrohtermal vent line
   TODO: sort (does not work if x1 > x2 or y1 > y2)"
  [s]
  (let [regex #"(\d+),(\d+)\s*->\s*(\d+),(\d+)"
        [_ x1 y1 x2 y2] (re-find regex s)]
    (map #(Integer. %) [x1 y1 x2 y2])))

