(ns advent.day04
  (:require [clojure.string :as s]))

(def SAMPLE-INPUT "resources/day04-test-input.txt")
(def ^:private INPUT-TXT    "resources/day04-input.txt")
(def ^:private BOARD-SIZE   5)
(def ^:private COORDS (for [x (range BOARD-SIZE) y (range BOARD-SIZE)]
                           [x y]))

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

(defn each-line-is-seq-of-int [lines]
  (->> lines
    (map parse-line ,,,)))

(defn each-board-is-seq-of-cells [board]
  (->> board
    (reduce into [] ,,,)
    ((fn [cells] (map #(vector %1 %2) cells COORDS)) ,,,)
    (map (fn [[value [x y]]] (->Cell value x y)) ,,,)))

(defn- group-lines-by-board [lines]
  (->> lines
    (partition (inc BOARD-SIZE) ,,,)
    (map #(drop 1 %) ,,,)
    ))

(defn make-boards [lines]
  (->> lines
    (group-lines-by-board ,,,)
    (map each-line-is-seq-of-int ,,,)
    (map each-board-is-seq-of-cells ,,,)
    ))

(defn load-lines [file-name]
  (->> file-name
    (slurp)
    (#(s/split % #"\r\n") ,,,)))

; ---
(defn make-game []
  (let [lines (load-lines SAMPLE-INPUT)])
    (->Game (load-numbers(first lines))
            (make-boards (rest lines))))


