(ns FibRecur)

(defn rfib
  ([n a b]
   (if (= 0 n)
     b
     (recur (dec n) b (+ a b))))
  ([n]
   (cond
     (< n 1) nil
     (<= n 2) 1
     :else (rfib (- n 2) 1 1))))

(defn -main []
  (println "Calculating fib(20)...")
  (time (println (rfib 30)))
  )

