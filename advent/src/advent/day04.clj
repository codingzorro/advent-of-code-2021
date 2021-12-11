(ns advent.day04
  (:require [clojure.string :as s]
            [clojure.pprint :as pp]))

(def SAMPLE-INPUT "resources/day04-test-input.txt")
(def ^:private INPUT-TXT    "resources/day04-input.txt")
(def ^:private BOARD-SIZE   5)
(def ^:private COORDS (for [row (range BOARD-SIZE) col (range BOARD-SIZE)]
                           [row col]))

(defrecord Game [numbers boards])
(defrecord Cell [value row col])
(defrecord Board [rows cols])

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
    (map (fn [[value [row col]]] (->Cell value row col)) ,,,)
    ))


(defn- group-lines-by-board [lines]
  (->> lines
    (partition (inc BOARD-SIZE) ,,,)
    (map #(drop 1 %) ,,,)
    ))

(defn- to-rows-and-columns [seq-of-cells]
  (let [rows    (map (fn [n] (filter #(= n (:row %)) seq-of-cells))
                     (range BOARD-SIZE))
        columns (map (fn [n] (filter #(= n (:col %)) seq-of-cells))
                     (range BOARD-SIZE))]
    (->Board rows columns)))

(defn make-boards [lines]
  (->> lines
    (group-lines-by-board ,,,)
    (map each-line-is-seq-of-int ,,,)
    (map each-board-is-seq-of-cells ,,,)
    (map to-rows-and-columns ,,,)
    ))

(defn load-lines [file-name]
  (->> file-name
    (slurp)
    (#(s/split % #"\r\n") ,,,)))

; ---
(defn all-in? [cells set-of-numbers]
  (every? set-of-numbers (map :value cells)))

(defn is-bingo-board? [board numbers]
  (or (seq (filter #(all-in? % numbers) (:rows board)))
      (seq (filter #(all-in? % numbers) (:cols board)))))

(defn play [game]
  (let [boards (:boards game)]
    (loop [next-numbers (:numbers game)
           past-numbers #{}]
      (let [winner (filter #(is-bingo-board? % past-numbers) boards)]
        (cond (empty? next-numbers) "no winners"
              (seq winner) winner
              :otherwise (recur (rest next-numbers) (conj past-numbers (first next-numbers))))
              ))))

(defn make-game []
  (let [lines (load-lines SAMPLE-INPUT)]
    (->Game (load-numbers(first lines))
            (make-boards (rest lines)))))


(def game (make-game))

(pp/pprint (play game))
