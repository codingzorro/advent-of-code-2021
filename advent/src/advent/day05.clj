(ns advent.day05
  (:require [advent.utilities :as u]
            [clojure.string :as string]))


(def TEST-INPUT "resources/day05-morning-test-input.txt")
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
  [two-points]
  (if (= (:x1 two-points) (:x2 two-points))
    (let [[from to] (sort [(:y1 two-points) (:y2 two-points)])]
      {:constant :x1, :from from, :to to})
    (let [[from to] (sort [(:x1 two-points) (:x2 two-points)])]
      {:constant :y1, :from from, :to to})))


(defn line-type [two-points]
  :horizontal-vertical)

; Generates the points defined by a Two-Points instance
(defmulti generate-points line-type)

(defmethod generate-points
  :horizontal-vertical
  [two-points]
  (let [{:keys [constant from to]} (generator-parameters two-points)]
    (if (= constant :x1)
      (into [] (map #(vector %1 %2) (repeat (constant two-points))
                                    (range from (inc to))))
      (into [] (map #(vector %1 %2) (range from (inc to))
                                    (repeat (constant two-points)))))))

(defn morning-puzzle
  "solves the morning puzzle of Day 5"
  [file-name]
  (->> file-name
    parse-file
    (map generate-points ,,,)
    (reduce into [] ,,,)
    (#(into % [[1 3]]) ,,,)
    sort
    frequencies
    (filter #(> (second %) 1) ,,,)
    count))

(morning-puzzle REAL-INPUT)
