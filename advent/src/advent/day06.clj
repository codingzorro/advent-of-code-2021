(ns advent.day06)

(def LIFETIME 7)


(defn birth-dates [days-to-go]
  (take-while #(<= % days-to-go) (iterate #(+ % 7) 6)))


