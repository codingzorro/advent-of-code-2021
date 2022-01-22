(ns advent.day07-test
  (:require [clojure.test :refer :all]
            [advent.day07 :refer :all]
            [clojure.pprint :as pp]))


; data from https://adventofcode.com/2021/day/7
(def test-data [16 1 2 0 4 2 7 1 2 14])


(deftest test-load-data
    (testing "load-data function"
      (is (= (load-data test-data)
             [#advent.day07.Crabs{:position 0, :number 1}
              #advent.day07.Crabs{:position 1, :number 2}
              #advent.day07.Crabs{:position 2, :number 3}
              #advent.day07.Crabs{:position 4, :number 1}
              #advent.day07.Crabs{:position 7, :number 1}
              #advent.day07.Crabs{:position 14, :number 1}
              #advent.day07.Crabs{:position 16, :number 1}]))))


(deftest test-split
  (testing "split function"
    (let [data (load-data test-data)]
      (is (=
            (split (second data) data)
            [#advent.day07.Crabs{:position 1, :number 2}
             '(#advent.day07.Crabs{:position 0, :number 1}
               #advent.day07.Crabs{:position 2, :number 3}
               #advent.day07.Crabs{:position 4, :number 1}
               #advent.day07.Crabs{:position 7, :number 1}
               #advent.day07.Crabs{:position 14, :number 1}
               #advent.day07.Crabs{:position 16, :number 1})])))))

(deftest test-fuel
  (testing "fuel function"
    (is (= (fuel 2 #advent.day07.Crabs{:position 2, :number 3}) 0))
    (is (= (fuel 2 #advent.day07.Crabs{:position 4, :number 1}) 2))
    (is (= (fuel 2 #advent.day07.Crabs{:position 16, :number 1}) 14))
    ))

(deftest test-total-fuel
  (testing "total fuel"
  (is (= (total-fuel 1 test-data) 41))
  (is (= (total-fuel 2 test-data) 37))
  (is (= (total-fuel 3 test-data) 39))
  (is (= (total-fuel 10 test-data) 71))
  ))

(least-fuel test-data)


