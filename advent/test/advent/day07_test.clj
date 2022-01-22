(ns advent.day07-test
  (:require [clojure.test :refer :all]
            [advent.day07 :refer :all]
            [clojure.pprint :as pp]))


; data from https://adventofcode.com/2021/day/7
(def test-data [16 1 2 0 4 2 7 1 2 14])


(deftest test-load-data
    (testing "load-data function"
      (is (= (load-data test-data)
             [#advent.day07.Crabs{:value 0, :position 1}
              #advent.day07.Crabs{:value 1, :position 2}
              #advent.day07.Crabs{:value 2, :position 3}
              #advent.day07.Crabs{:value 4, :position 1}
              #advent.day07.Crabs{:value 7, :position 1}
              #advent.day07.Crabs{:value 14, :position 1}
              #advent.day07.Crabs{:value 16, :position 1}]))))


(deftest test-fuel
  (testing "fuel function"
    (let [crabs (load-data test-data)
          crabs-0 (crabs 0)
          crabs-2 (crabs 2)]
      (is (= (fuel crabs-0 crabs-2) 6)))))

