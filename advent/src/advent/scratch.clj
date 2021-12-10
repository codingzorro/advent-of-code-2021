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

(defn bingo? [board]
  false)

(loop [next-numbers (:numbers game) read-numbers #{}]
  (cond
    (empty? next-numbers) "aus maus"
    (some bingo? (:boards game))(some bingo? (:boards game))
    :default (recur (rest next-numbers)
                    (conj read-numbers (first next-numbers)))))

(def read-numbers (into #{} (drop 20 (:numbers game))))
(def b (first (:boards game)))
(def row (first (:rows b)))
(every? read-numbers (map :value row))

(pp/pprint b)
