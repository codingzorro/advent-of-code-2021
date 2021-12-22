(ns advent.day05
  (:require [advent.utilities :as u]
            [clojure.string :as string]))


(def TEST-INPUT "resources/day05-afternoon-test-input.txt")
(def REAL-INPUT "resources/day05-real-input.txt")


(defrecord Two-Points [x1 y1 x2 y2])

(defn line-to-line
  "parses the four coordinates from a file line"
  [s]
  (let [regex #"(\d+),(\d+)\s*->\s*(\d+),(\d+)"
        [_ x1 y1 x2 y2] (re-find regex s)]
    (map #(Integer. %) [x1 y1 x2 y2])))

(defn horizontal-or-vertical? [two-points]
  (let [{:keys [x1 x2 y1 y2]} two-points]
    (or (= x1 x2) (= y1 y2))))

(defn diagonal? [two-points]
  (let [{:keys [x1 x2 y1 y2]} two-points
        delta (fn [a b] (Math/abs (- a b)))]
    (= (delta x1 x2) (delta y1 y2))))

(defn parse-file
  "convert the relevant lines in the file to `Two-Points` instances"
  [f file-name]
  (->> file-name
    (slurp ,,,)
    (#(string/split % #"\n") ,,,)
    (map line-to-line ,,,)
    (map #(apply ->Two-Points %) ,,,)
    (filter f ,,,)
    ))


(defn generator-parameters
  "deliver the parameters to transform [(1,1) (1,3)]' to
   [(1,1) (1,2) (1,3)]"
  [{:keys [x1 y1 x2 y2]}]
  (if (= x1 x2)
    (let [[from to] (sort [y1 y2])]
      {:constant :x1, :from from, :to to})
    (let [[from to] (sort [x1 x2])]
      {:constant :y1, :from from, :to to})))


(defn line-type [two-points]
  (cond
        (diagonal? two-points)               :diagonal
        (horizontal-or-vertical? two-points) :horizontal-vertical
        :otherwise                           :unknown))

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

(defmethod generate-points
  :diagonal
  [two-points]
  (let [{:keys [x1 y1 x2 y2]} two-points]
    (if (< x1 x2)
        (if (< y1 y2)
            (into [] (map #(vector %1 %2) (range x1 (inc x2))
                                          (range y1 (inc y2))))
            (into [] (map #(vector %1 %2) (range x1 (inc x2))
                                          (range y1 (dec y2) -1))))
        (if (< y1 y2)
            (sort (into [] (map #(vector %1 %2) (range x1 (dec x2) -1)
                                                (range y1 (inc y2)))))
            (sort (into [] (map #(vector %1 %2) (range x1 (dec x2) -1)
                                                (range y1 (dec y2) -1))))))))

(defn puzzle
  "solves the afternoon puzzle of Day 5"
  [f file-name]
  (->> file-name
    (parse-file f ,,,)
    (map generate-points ,,,)
    (reduce into [] ,,,)
    sort
    frequencies
    (filter #(> (second %) 1) ,,,)
    count
  ))

(def with-diagonals #(or (horizontal-or-vertical? %) (diagonal? %)))
(def without-diagonals #(horizontal-or-vertical? %))
(println (puzzle without-diagonals REAL-INPUT))

