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
; (def board (nth (:boards game) 2))
; (def row (first (:rows board)))
; (pp/pprint (every? numbers (map :value row)))

;------
; (some #(good-lines % :rows numbers) (:boards game))
;------

(defn check-boards [game numbers]
  (filter #(or (good-lines % :rows numbers)
               (good-lines % :cols numbers)) (:boards game)))

(defn play [game]
  (let [boards (:boards game)]
    (loop [next-numbers (:numbers game)
           past-numbers #{}]
      (let [winners  (check-boards game past-numbers)]
        (cond (empty? next-numbers) "no winners"
              (seq winners) :foo ;[past-numbers (first winners)]
              :otherwise (recur (rest next-numbers) (conj past-numbers (first (next-numbers))))
              )))))

https://twitter.com/nayibbukele/status/1469045510442864642?s=20
(play game)
https://twitter.com/Cernovich/status/1469168999950798849?s=20

; (def b (first (:boards game)))
; (check-boards game #{})


; (pp/pprint (some #(good-lines % :rows numbers) (:boards game)))
