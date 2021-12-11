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
;   (some #(every? past-numbers %) ,,,)
    ))

;------
(def board (nth (:boards game) 2))
(def row (first (:rows board)))
(pp/pprint
  (every? numbers (map :value row))
  )

(defn all-in? [cells numbers]
  (every? numbers (map :value cells)))

(defn good-lines [board rows-or-cols numbers]
  (seq (filter #(all-in? % numbers) (rows-or-cols board))))

;------
(some #(good-lines % :rows numbers) (:boards game))
;------

(def numbers (into #{} (take 12 (:numbers game))))
(defn check-boards [game numbers]
  (filter (or (good-lines board :rows numbers)
              (good-lines board :cols numbers))) (:boards game))

(defn bingo? [board past-numbers]
  (or (#(check-x-cells (:rows %) past-numbers) board)
      (#(check-x-cells (:cols %) past-numbers) board)))


(defn play [game]
  (let [boards (:boards game)]
    (loop [next-numbers (:numbers game)
           past-numbers #{}]
      (let [winners  (check-boards game past-numbers)]
        (cond (empty? next-numbers) "no winners"
              (seq winners) (first winners)
              :otherwise (recur (rest next-numbers) (conj past-numbers (first (next-numbers))))
              )))))

(pp/pprint (play game))

; (def b (first (:boards game)))


; (pp/pprint (some #(good-lines % :rows numbers) (:boards game)))
