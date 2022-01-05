(ns advent.day07-test
  (:require [clojure.test :refer :all]
            [advent.day07 :refer :all]
            [clojure.pprint :as pp]))


; data from https://adventofcode.com/2021/day/7
(def test-data [16 1 2 0 4 2 7 1 2 14])


(deftest test-other-crabs
    (testing "see if you can remove the zero-th element"
      (is (other-crabs (sort test-data) (vec '(1 1 2 2 2 4 7 14 16))))))

