(ns advent.day04
  (:require [advent.utilities :as u]
            [clojure.string :as string]))


(def TEST-INPUT "resources/day05-test-input.txt")
(def REAL-INPUT "resources/day05-real-input.txt")

(defrecord Coords [x1 y1 x2 y2])

(defn line-to-line
  "parses the four coordinates from a file line"
  [s]
  (let [regex #"(\d+),(\d+)\s*->\s*(\d+),(\d+)"
        [_ x1 y1 x2 y2] (re-find regex s)]
    (map #(Integer. %) [x1 y1 x2 y2])))


(defn parse-file
  "convert the relevant lines in the file to `Coords` instances"
  [file-name]
  (->> file-name
    (slurp ,,,)
    (#(string/split % #"\n") ,,,)
    (map line-to-line ,,,)
    (map #(apply ->Coords %) ,,,)
    (filter #(or (= (:x1 %) (:x2 %)) (= (:y1 %) (:y2 %))) ,,,)
    ))


(parse-file REAL-INPUT)

