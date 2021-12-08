(ns advent.day04
  (:require [clojure.string :as s]
            [clojure.pprint :as pp]))

(def ^:private sample-input "resources/day04-test-input.txt")
(def ^:private input-txt    "resources/day04-input.txt")

(defrecord Game [numbers boards])

(defn load-numbers [line]
  (->> line
    (#(s/split % #",") ,,,)
    (map #(Integer. %) ,,,)
    (into [] ,,,)
    ))

(defn parse-line [line]
  (->> line
    (#(s/split % #" ") ,,,)
    (filter seq ,,,)
    (map #(Integer. %) ,,,)
    ))

(defn to-array-of-arrays [lines]
  (->> lines
    (map parse-line ,,,)
    ))

(defn make-boards [lines]
  (->> lines
    (partition 6 ,,,)
    (map #(drop 1 %) ,,,)
    (map #(into [] %) ,,,)      ; each array is one board, i.e. has n lines
    (map to-array-of-arrays ,,,)
  ))

(defn load-lines [file-name]
  (->> file-name
    (slurp)
    (#(s/split % #"\r\n") ,,,)))

(def lines (load-lines sample-input))
(def game (->Game (load-numbers(first lines))
                   (make-boards (rest lines))))

;(pp/pprint (seq (first (second (first (:boards game))))))
(pp/pprint game)
