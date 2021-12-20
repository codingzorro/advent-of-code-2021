(ns advent.utilities)

(defn  apply-to-file
  "provides the lines of the file as a seq of lines to the passed function"
  [f file-name]
  (with-open [rdr (clojure.java.io/reader file-name)]
    (->> (line-seq rdr)
    (f ,,,))))

(defn print-file
  "print line by line the contents of a file"
  [f file-name]
  (with-open [rdr (clojure.java.io/reader file-name)]
    (doseq [line (line-seq rdr)]
      (println (f line)))))



