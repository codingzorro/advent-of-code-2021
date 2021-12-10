(ns advent.scratch
  (:require [advent.day04 :as d]
            [clojure.pprint :as pp]))

; (pp/pprint (d/make-game))

(def game (d/make-game))

; (pp/pprint
; (->> d/SAMPLE-INPUT
;   (d/load-lines)
;   (rest)
;   (d/make-boards)
;   (take 1)
;   ))

(defn check-x-cells [rows-or-cols past-numbers]
  (->> rows
    (map #(map :value %) ,,,)
    (some #(every? past-numbers %) ,,,)
    ))

(defn bingo? [board past-numbers]
  (or (check-x-cells (:rows board) past-numbers)
      (check-x-cells (:cols board) past-numbers)))


(defn play [game]
  (let [boards (:boards game)]
    (loop [next-numbers (:numbers game)
           past-numbers #{}]
      (let [next-number  (first next-numbers)]
        (if (or (empty? next-numbers) (some #(bingo? % past-numbers) boards))
           past-numbers
           (recur (rest next-numbers) (conj past-numbers next-number)))))))

(play game)

; (def b (first (:boards game)))
; (def rows (:rows b))
; (def numbers (into #{} (take 10 (:numbers game))))


; (every? numbers (map :value (first rows)))

; (first (map #(map :value %) rows))

; (check-x-cells (:rows b))
