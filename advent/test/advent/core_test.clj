(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.day04 :refer :all]
            [clojure.pprint :as pp]))

(def PAST-NUMBERS (set [7 4 9 5 11 17 23 2 0 14 21 24]))
(def REFERENCE-COL [15 18 8 11 21])
(def REFERENCE-ROW [14 21 17 24 4])

(def test-game (make-game))
(def board-2 (second (:boards test-game)))
(def board-3 (nth (:boards test-game) 2))

(deftest board-2-col-2-test
  (testing "can you get a col"
    (let [board-2-col-2 (second (:cols board-2))]
      (is (= (map :value board-2-col-2) (seq REFERENCE-COL))))))

(deftest board-3-row-1-test
  (testing "can you get a row"
    (let [board-3-row-1 (first (:rows board-3))]
      (is (= (map :value board-3-row-1) (seq REFERENCE-ROW))))))

(deftest col-is-not-bingo-test
  (testing "detect a non-Bingo row"
    (let [board-2-col-2 (second (:cols board-2))]
      (is (not (all-in? board-2-col-2 PAST-NUMBERS))))))

(deftest row-is-bingo-test
  (testing "detect a Bingo row"
    (let [board-3-row-1 (first (:rows board-3))]
      (is (all-in? board-3-row-1 PAST-NUMBERS)))))

(def row1 (first (:rows board-3)))
(def col1 (second (:cols board-2)))

; (when (is-bingo-board? board-3 PAST-NUMBERS)
;   (pp/pprint "bingo!"))

(filter (fn [board] (is-bingo-board? board PAST-NUMBERS)) (:boards test-game))
(filter #(is-bingo-board? % PAST-NUMBERS) (:boards test-game))

