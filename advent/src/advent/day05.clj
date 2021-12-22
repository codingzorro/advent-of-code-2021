(ns advent.day05
  (:require [advent.utilities :as u]
            [clojure.string :as string]))


(def TEST-INPUT "resources/day05-test-input.txt")
(def REAL-INPUT "resources/day05-real-input.txt")

(defrecord Two-Points [x1 y1 x2 y2])

(defn line-to-line
  "parses the four coordinates from a file line"
  [s]
  (let [regex #"(\d+),(\d+)\s*->\s*(\d+),(\d+)"
        [_ x1 y1 x2 y2] (re-find regex s)]
    (map #(Integer. %) [x1 y1 x2 y2])))


(defn parse-file
  "convert the relevant lines in the file to `Two-Points` instances"
  [file-name]
  (->> file-name
    (slurp ,,,)
    (#(string/split % #"\n") ,,,)
    (map line-to-line ,,,)
    (map #(apply ->Two-Points %) ,,,)
    (filter #(or (= (:x1 %) (:x2 %)) (= (:y1 %) (:y2 %))) ,,,)
    ))


(def sample (first (parse-file TEST-INPUT)))
(def sample-2 (second (parse-file TEST-INPUT)))

(defn generator-parameters
  "deliver the parameters to transform [(1,1) (1,3)]' to
   [(1,1) (1,2) (1,3)]"
  [coords]
  (if (= (:x1 coords) (:x2 coords))
    (let [[from to] (sort [(:y1 coords) (:y2 coords)])]
      {:constant :x1, :from from, :to to})
    (let [[from to] (sort [(:x1 coords) (:x2 coords)])]
      {:constant :y1, :from from, :to to})))


(defn generate-points
  "Generates the points from and including start and end point"
  [coords]
  (let [{:keys [constant from to]} (generator-parameters coords)]
    (if (= constant :x1)
      (into [] (map #(vector %1 %2) (repeat (constant coords))
                                    (range from (inc to))))
      (into [] (map #(vector %1 %2) (range from (inc to))
                                    (repeat (constant coords)))))))

(defn morning-puzzle
  "solves the morning puzzle of Day 5"
  [file-name]
  (->> REAL-INPUT
    parse-file
    (map generate-points ,,,)
    (reduce into [] ,,,)
    (#(into % [[1 3]]) ,,,)
    sort
    frequencies
    (filter #(> (second %) 1) ,,,)
    count))

(morning-puzzle REAL-INPUT)
