(ns Fibs)

(defn fib [n]
  (cond
    (< n 1) nil
    (<= n 2) 1
    :else (+ (fib (- n 1)) (fib (- n 2)) )))

(defn fibs [n]
  map fib (range 1 (inc n)))

(defn -main []
  (println "Calculating fib(20)...")
  (time (println (fib 20)))
  )

