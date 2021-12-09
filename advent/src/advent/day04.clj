(ns advent.day04
  (:require [clojure.string :as s]))

(def ^:private sample-input "resources/day04-test-input.txt")
(def ^:private input-txt    "resources/day04-input.txt")

(defrecord Game [numbers boards])
(defrecord Cell [value x y])

(defn load-numbers [line]
  (->> line
    (#(s/split % #",") ,,,)
    (map #(Integer. %) ,,,)
    (into [] ,,,)))

(defn parse-line [line]
  (->> line
    (#(s/split % #" ") ,,,)
    (filter seq ,,,)
    (map #(Integer. %) ,,,)))

(defn to-array-of-arrays [lines]
  (->> lines
    (map parse-line ,,,)))

(defn make-board [board]
  (let [coords (for [x (range 5) y (range 5)] [x y])]
    (->> board
      (reduce into [] ,,,)
      ((fn [cells] (map #(vector %1 %2) cells coords)) ,,,)
      (map (fn [[value [x y]]] (->Cell value x y)) ,,,))))

(defn make-boards [lines]
  (->> lines
    (partition 6 ,,,)
    (map #(drop 1 %) ,,,)
    (map #(into [] %) ,,,)      ; each array is one board, i.e. has n lines
    (map to-array-of-arrays ,,,)
    (map make-board ,,,)))

(defn load-lines [file-name]
  (->> file-name
    (slurp)
    (#(s/split % #"\r\n") ,,,)))

; ---
(defn make-game []
  (let [lines (load-lines sample-input)])
    (->Game (load-numbers(first lines))
            (make-boards (rest lines))))
