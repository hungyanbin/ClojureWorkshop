(ns FibMem)

(declare fib)

(defn fib-w [n]
  (cond
    (< n 1) nil
    (<= n 2) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))

(def fib
  (memoize fib-w))

(defn -main []
  (println "Calculating fib(20)...")
  (time (println (fib 40)))
  )

