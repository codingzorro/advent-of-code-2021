(ns advent.day04
  (:require [clojure.set :as s]
            [clojure.string :as string]
            [clojure.pprint :as pp]))

(def SAMPLE-INPUT "resources/day04-test-input.txt")
(def ^:private INPUT-TXT    "resources/day04-real-input.txt")
(def ^:private BOARD-SIZE   5)
(def ^:private COORDS (for [row (range BOARD-SIZE) col (range BOARD-SIZE)]
                           [row col]))

(defrecord Game [numbers boards])
(defrecord Cell [value row col])
(defrecord Board [rows cols])
(defrecord Winner [board past-numbers])

(defn load-numbers [line]
  (->> line
    (#(string/split % #",") ,,,)
    (map #(Integer. %) ,,,)
    (into [] ,,,)))

(defn parse-line [line]
  (->> line
    (#(string/split % #" ") ,,,)
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
    (#(string/split % #"\r\n") ,,,)))

; ---
(defn all-in? [cells set-of-numbers]
  (every? set-of-numbers (map :value cells)))

(defn is-bingo-board? [board numbers]
  (let [numbers (set numbers)]
    (or (seq (filter #(all-in? % numbers) (:rows board)))
        (seq (filter #(all-in? % numbers) (:cols board))))))

(defn morning-play
  "returns the winning board"
  [game]
  (let [boards (:boards game)]
    (loop [next-numbers (:numbers game)
           past-numbers []]
      (let [winner (filter #(is-bingo-board? % past-numbers) boards)]
        (cond (empty? next-numbers) "no winners"
              (seq winner) (->Winner (first winner) past-numbers)
              :otherwise (recur (rest next-numbers) (conj past-numbers (first next-numbers))))))))

(defn make-game []
  (let [lines (load-lines INPUT-TXT)]
    (->Game (load-numbers(first lines))
            (make-boards (rest lines)))))


(defn score [winner]
  (let [rows               (:rows (:board winner))
        numbers-on-board   (map :value (apply concat rows))
        called-numbers     (:past-numbers winner)
        last-number        (last called-numbers)
        non-called-numbers (s/difference (set numbers-on-board)
                                         (set called-numbers))]
    (* (apply + non-called-numbers) last-number)))


; print the morning score
(pp/pprint
(->> (make-game)
  morning-play
  score)
)
