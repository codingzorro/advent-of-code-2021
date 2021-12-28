(ns advent.day06
(:require [advent.utilities :as u]
            [clojure.string :as string]))

(def LIFETIME 7)

(def bd-lookup (atom {}))
(def fm-lookup (atom {}))

(defn birth-dates
  "find the 'date' when a new fish are born depending on the parent's counter"
  [^Integer counter ^Integer days-to-go]
  (let [check #(<= % days-to-go)]
    (take-while check
               (map #(+ (inc counter) %) (take-while check
                                                     (range 0 days-to-go 7))))))

(defn bd [^Integer counter ^Integer days-to-go]
  (or
    (@bd-lookup [counter days-to-go])
    (let [new-value (birth-dates counter days-to-go)]
          (swap! bd-lookup assoc [counter days-to-go] new-value)
          new-value)))


(defn fish-maker [[^Integer counter ^Integer days-to-go]]
  (let [birth-dates (or
                      (@bd-lookup [counter days-to-go])
                      (let [new-value (birth-dates counter days-to-go)]
                            (swap! bd-lookup assoc [counter days-to-go] new-value)
                            new-value))
        new-days-to-go (- days-to-go 3)
        values (pmap #(- new-days-to-go %) birth-dates)]
    (vec (map #(vector %1 %2) (repeat 5) values))))

(def DAYS 256)
(def sample [3 4 3 1 2])
;(def sample [5,1,5,3,2,2,3,1,1,4,2,4,1,2,1,4,1,1,5,3,5,1,5,3,1,2,4,4,1,1,3,1,1,3,1,1,5,1,5,4,5,4,5,1,3,2,4,3,5,3,5,4,3,1,4,3,1,1,1,4,5,1,1,1,2,1,2,1,1,4,1,4,1,1,3,3,2,2,4,2,1,1,5,3,1,3,1,1,4,3,3,3,1,5,2,3,1,3,1,5,2,2,1,2,1,1,1,3,4,1,1,1,5,4,1,1,1,4,4,2,1,5,4,3,1,2,5,1,1,1,1,2,1,5,5,1,1,1,1,3,1,4,1,3,1,5,1,1,1,5,5,1,4,5,4,5,4,3,3,1,3,1,1,5,5,5,5,1,2,5,4,1,1,1,2,2,1,3,1,1,2,4,2,2,2,1,1,2,2,1,5,2,1,1,2,1,3,1,3,2,2,4,3,1,2,4,5,2,1,4,5,4,2,1,1,1,5,4,1,1,4,1,4,3,1,2,5,2,4,1,1,5,1,5,4,1,1,4,1,1,5,5,1,5,4,2,5,2,5,4,1,1,4,1,2,4,1,2,2,2,1,1,1,5,5,1,2,5,1,3,4,1,1,1,1,5,3,4,1,1,2,1,1,3,5,5,2,3,5,1,1,1,5,4,3,4,2,2,1,3])
(def data (vec (map #(vector %1 %2) sample (repeat DAYS))))



(defn -main [& args]
  (time
    (println
      (let [all-fish (atom (reduce conj clojure.lang.PersistentQueue/EMPTY data))]
        (loop [result 0]
          (if (empty? @all-fish)
            result
            (let [the-fish (peek @all-fish)
                  new-fish (let [[^Integer counter ^Integer days-to-go] the-fish]
                                      (or
                                        (@fm-lookup [counter days-to-go])
                                        (let [new-value (fish-maker [counter days-to-go])]
                                                (swap! fm-lookup assoc [counter days-to-go] new-value)
                                                new-value)))]
;             (doseq [f new-fish] (swap! all-fish conj f))
              (swap! all-fish into new-fish)
              (swap! all-fish pop)
              (when (zero? (rem result 100000000)) (println result (count @bd-lookup) (count @fm-lookup)))
              (recur (inc result))))
            ) ; loop
          ) ; let
      ) ; println
    ) ; time
  ) ; main
