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


(defn play [game]
  (let [boards (:boards game)]
    (loop [next-numbers (:numbers game)
           past-numbers #{}]
      (let [next-number  (first next-numbers)]
        (if (or (empty? next-numbers) (some bingo? boards))
           past-numbers
           (recur (rest next-numbers) (conj past-numbers next-number)))))))

(play game)
