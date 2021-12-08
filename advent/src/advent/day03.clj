(ns advent.day03)

(def ^:private mini-data ["00100" "11110" "10110" "10111" "10101" "01111"
                          "00111" "11100" "10000" "11001" "00010" "01010"])

(defn- half-length [data]
  (quot (count data) 2))

(defn- to-int-vector [row]
  (map #(Integer. (str %)) row))

(defn- plus [v1 v2]
  (->> v2
  (map vector v1)
  (map #(apply + %))))

(defn- zero-or-one [no-of-ones max-score default]
  (let [no-of-zeros (- max-score no-of-ones)]
    (cond
      (> no-of-ones no-of-zeros) 1
      (< no-of-ones no-of-zeros) 0
      :otherwise                 default)))

(defn- lines-to-bit-arrays [lines]
  (map to-int-vector lines))

(defn- bit-arrays-to-frequencies-of-one [lines]
  (let [no-of-bits (count (first lines))
        result (into [] (repeat no-of-bits 0))]
    (->> lines
      (lines-to-bit-arrays ,,,)
      (reduce plus result ,,,))))

(defn- frequencies-of-one-to-binary-string [max-score default frequencies-of-one]
  (->> frequencies-of-one
    (map #(zero-or-one % max-score default) ,,,)
    (apply str ,,,)))

(defn- negate-binary-string [bin-string]
  (let [inverter {\0 \1, \1 \0}]
    (->> bin-string
      (map inverter ,,,)
      (apply str ,,,))))

(defn- to-integer [bin-string]
  (Integer/parseInt bin-string 2))

(defn- apply-to-file [f file-name]
  (with-open [rdr (clojure.java.io/reader file-name)]
    (->> (line-seq rdr)
    (f ,,,))))

(defn- char-at [s pos]
  ((into [] s) pos))

; ----------- the main functions

(defn gamma-rate
  "input: lines of bitstrings
   output: the gamma rate as a binary number"
  [lines]
  (let [max-score (count lines)]
    (->> lines
      (bit-arrays-to-frequencies-of-one ,,,)
      (frequencies-of-one-to-binary-string max-score 1 ,,,)
      )))

(defn epsilon-rate
  "input: lines of bitstrings
   output: the epsilon rate as a binary number"
  [lines]
  (negate-binary-string (gamma-rate lines)))

(defn power-consumption [data]
  (let [gamma-rate   (to-integer (gamma-rate data))
        epsilon-rate (to-integer (epsilon-rate data))]
    (* gamma-rate epsilon-rate)))


; -- afternoon

(defn- select [lines pos check-bit]
  (filter #(= (char-at % pos) check-bit) lines))

(defn- most-popular-bit [lines pos default]
  (let [max-score (count lines)]
    (->> lines
      (bit-arrays-to-frequencies-of-one ,,,)
      (frequencies-of-one-to-binary-string max-score default ,,,)
      (#(char-at % pos) ,,,)
      )))

(defn- least-popular-bit [lines pos default]
  (let [lookup {\0 \1, \1 \0}]
    (lookup (most-popular-bit lines pos (lookup default)))))

(defn- gas-rating [lines [bit-getter default-bit]]
  (loop [lines lines, pos 0]
    (if (or (<= (count lines) 1) (= pos (count (first lines))))
      (and (or
             nil
             (to-integer (first lines))))
      (let [check-bit      (bit-getter lines pos default-bit)
            selected-lines (select lines pos check-bit)]
        (recur selected-lines (inc pos))))))


(def ^:private config {:oxygen [most-popular-bit \1]
                       :co2    [least-popular-bit \0]})

; -- the solutions

(defn morning-puzzle
  "store the contents of a file as a vector of lines"
  [file-name]
  (apply-to-file power-consumption file-name))

(defn afternoon-puzzle [file-name]
  (* (apply-to-file #(gas-rating % (config :oxygen)) file-name)
     (apply-to-file #(gas-rating % (config :co2)) file-name)))

(println (morning-puzzle  "resources/day03-input.txt"))
(println (afternoon-puzzle "resources/day03-input.txt"))
