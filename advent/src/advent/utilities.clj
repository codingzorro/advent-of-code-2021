(ns advent.utilities)

(defn  apply-to-file [f file-name]
  (with-open [rdr (clojure.java.io/reader file-name)]
    (->> (line-seq rdr)
    (f ,,,))))


