# Clojure Workshop

## 1. Introduction to Clojure and Function Syntax

Clojure is a modern, dynamic, and functional programming language that runs on the Java Virtual Machine (JVM). It's a dialect of Lisp, which means it follows the "code as data" philosophy and uses a prefix notation for function calls.

### Key Characteristics of Clojure:

- **Functional Programming**: Emphasizes pure functions and immutable data structures
- **Lisp Dialect**: Uses S-expressions and homoiconicity (code as data)
- **JVM-based**: Leverages the Java ecosystem and interoperability
- **Concurrency-oriented**: Designed for modern multi-core processors

### Function Syntax

In Clojure, everything is expressed as a function call using prefix notation. This means the function name comes first, followed by its arguments:

```clojure
(function-name argument1 argument2 ...)
```

Examples:

```clojure
;; Addition
(+ 1 2 3)  ; => 6

;; String concatenation
(str "Hello, " "world!")  ; => "Hello, world!"

;; Nested function calls
(+ (* 2 3) (- 10 5))  ; => 11
```

This uniform syntax makes Clojure code consistent and easy to parse, both for humans and computers.

## 2. If and Cond Syntax

### If Expression

The `if` expression in Clojure has the following syntax:

```clojure
(if condition
  then-expression
  else-expression)
```

The `if` expression evaluates the condition. If it's truthy (anything other than `nil` or `false`), it evaluates and returns the then-expression. Otherwise, it evaluates and returns the else-expression.

Example:

```clojure
(if (> 5 3)
  "5 is greater than 3"
  "5 is not greater than 3")
; => "5 is greater than 3"
```

### Cond Expression

The `cond` expression allows for multiple conditions to be tested in sequence:

```clojure
(cond
  condition1 expression1
  condition2 expression2
  ...
  :else default-expression)
```

The conditions are evaluated in order. The first condition that evaluates to true will have its corresponding expression evaluated and returned. If no condition is true, the `:else` clause (if provided) is used.

Example from your Fibs.clj:

```clojure
(defn fib [n]
  (cond
    (< n 1) nil
    (<= n 2) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))
```

In this example:
- If n < 1, return nil
- If n ≤ 2, return 1
- Otherwise, calculate fib(n-1) + fib(n-2)

## 3. Defn and Def Syntax

### Def

The `def` special form creates and interns a global var with the specified name and value:

```clojure
(def name value)
```

Example:

```clojure
(def pi 3.14159)
(def greeting "Hello, Clojure!")
```

### Defn

The `defn` macro defines a function:

```clojure
(defn function-name
  "Optional docstring"
  [parameters]
  function-body)
```

Example:

```clojure
(defn square
  "Returns the square of x"
  [x]
  (* x x))
```

Functions can have multiple arities (different parameter counts):

```clojure
(defn greeting
  ([name] (str "Hello, " name "!"))
  ([title name] (str "Hello, " title " " name "!")))
```


## 4. Vectors and Lists and Structure Sharing

### Lists

Lists are sequential collections in Clojure, written with parentheses:

```clojure
'(1 2 3 4)  ; The quote prevents evaluation as a function call
(list 2, 3, 4)  ; Using the list function
```

Lists are optimized for adding or removing items from the beginning:

```clojure
(first '(1 2 3))  ; => 1
(rest '(1 2 3))   ; => (2 3)
(cons 0 '(1 2 3)) ; => (0 1 2 3)
```

### Vectors

Vectors are indexed collections, written with square brackets:

```clojure
[1 2 3 4]
(vec 1, 2, 3)  ; Using the vec function
```

Vectors are optimized for random access and appending to the end:

```clojure
(get [1 2 3] 1)      ; => 2
(nth [1 2 3] 1)      ; => 2
(conj [1 2 3] 4)     ; => [1 2 3 4]
(assoc [1 2 3] 1 42) ; => [1 42 3]
```

### Structure Sharing

![Persistent Vector Structure](https://hypirion.com/sha/fbeb457c894952aaa43270a4682f709f3c9ddd28b83a076a91152389ece301dc.webp)

Clojure's data structures are immutable, meaning they cannot be changed after creation. When you "modify" a data structure, you actually create a new version that shares most of its structure with the original.

#### How Persistent Vectors Work

Clojure's persistent vectors are implemented as 32-way branching trees (also known as tries). This means:

1. Each node in the tree can have up to 32 children
2. Leaf nodes contain the actual values
3. Internal nodes contain references to other nodes
4. The tree is balanced, with all leaf nodes at the same depth

This structure allows for efficient:
- Random access (get operation)
- Updates (assoc operation)
- Appends (conj operation)

#### Example of Structure Sharing

When you add an element to a vector:

```clojure
(def v [1 2 3 4 5])
(def v2 (conj v 6))
```

The new vector `v2` shares most of its internal structure with `v`. Only the path to the new element needs to be created:

1. A new root node is created
2. Most child pointers in the new root point to the same nodes as in the original vector
3. Only the path to the new element contains new nodes
4. All unchanged subtrees are shared between both vectors

#### Performance Characteristics

- **get**: O(log32 n) - Lookup time is logarithmic
- **assoc**: O(log32 n) - Update time is logarithmic
- **conj**: O(log32 n) - Append time is logarithmic (amortized constant time in most cases)
- **pop**: O(log32 n) - Remove last element time is logarithmic

The base-32 logarithm means that even for very large vectors (billions of elements), operations only require a few steps.

#### Implementation Details

The implementation uses:

1. **Trie Data Structure**: A tree where paths represent keys
2. **Tail Optimization**: Recent additions are stored in a separate array for faster access
3. **Path Copying**: Only the path to a changed element is copied, not the entire structure

For a deeper understanding of how Clojure's persistent vectors work, check out these excellent blog posts:
- [Understanding Persistent Vector Part 1](https://hypirion.com/musings/understanding-persistent-vector-pt-1)
- [Understanding Persistent Vector Part 2](https://hypirion.com/musings/understanding-persistent-vector-pt-2)

## 5. Functions to Manipulate Vectors

### Basic Vector Operations

#### assoc

Adds or replaces an element at a specific index:

```clojure
(assoc [0 1 2 3 4] 2 42)  ; => [0 1 42 3 4]
```

#### take

Returns the first n elements of a collection:

```clojure
(take 3 [0 1 2 3 4 5])  ; => (0 1 2)
```

#### nth

Gets the element at a specific index:

```clojure
(nth [10 20 30 40] 2)  ; => 30
```

#### first

Gets the first element of a collection:

```clojure
(first [5 6 7 8])  ; => 5
```

#### rest

Returns all elements except the first:

```clojure
(rest [5 6 7 8])  ; => (6 7 8)
```

#### into

Adds all items from a collection into another collection:

```clojure
(into [1 2 3] [4 5 6])  ; => [1 2 3 4 5 6]
(into [] '(1 2 3))      ; => [1 2 3]
```

### Higher-Order Functions

#### map

Applies a function to each element in a collection:

```clojure
(map inc [1 2 3 4])  ; => (2 3 4 5)

;; Map with a function that takes two arguments
(map + [1 2 3] [4 5 6])  ; => (5 7 9)
```

#### range

Creates a sequence of numbers:

```clojure
(range 5)     ; => (0 1 2 3 4)
(range 1 6)   ; => (1 2 3 4 5)
(range 0 10 2) ; => (0 2 4 6 8)
```

## 6. Walkthrough of the Fibs Code

Let's analyze the Fibonacci implementations in your project:

### Basic Recursive Implementation (Fibs.clj)

```clojure
(defn fib [n]
  (cond
    (< n 1) nil
    (<= n 2) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))
```

This is a direct translation of the mathematical definition of Fibonacci numbers:
- F(1) = F(2) = 1
- F(n) = F(n-1) + F(n-2) for n > 2

The function handles edge cases:
- If n < 1, it returns nil (invalid input)
- If n ≤ 2, it returns 1 (base cases)
- Otherwise, it recursively calculates F(n-1) + F(n-2)

The problem with this implementation is that it recalculates the same values multiple times, leading to exponential time complexity O(2^n).

There's also a function to generate a sequence of Fibonacci numbers:

```clojure
(defn fibs [n]
  (map fib (range 1 (inc n))))
```

### Memoized Implementation (FibMem.clj)

```clojure
(declare fib)

(defn fib-w [n]
  (cond
    (< n 1) nil
    (<= n 2) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))

(def fib
  (memoize fib-w))
```

This implementation uses memoization to avoid recalculating the same values:

1. First, it declares `fib` to allow for forward reference
2. It defines `fib-w` with the same logic as the basic implementation
3. It defines `fib` as a memoized version of `fib-w`

When `fib` is called with a value, it checks if it has already calculated the result for that value. If so, it returns the cached result; otherwise, it calculates and caches the new result.

This reduces the time complexity from O(2^n) to O(n), making it much more efficient for larger values of n.

### Tail-Recursive Implementation (FibRecur.clj)

```clojure
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
```

This implementation uses tail recursion with the `recur` special form:

1. The single-argument version handles the base cases and sets up the initial call to the three-argument version
2. The three-argument version uses an iterative approach with accumulators:
   - `a` and `b` represent consecutive Fibonacci numbers
   - Each recursive call shifts the window: `a` becomes `b`, and `b` becomes `a + b`
   - After n iterations, `b` holds the nth Fibonacci number

This implementation is both efficient (O(n) time complexity) and stack-safe due to the use of `recur`, which performs tail-call optimization.

## 7. Binomial Coefficients

The binomial coefficient C(n,k) represents the number of ways to choose k items from a set of n items.

### Pascal's Triangle

Pascal's triangle is a visual representation of binomial coefficients:

```
                1
              1   1
            1   2   1
          1   3   3   1
        1   4   6   4   1
      1   5  10  10   5   1
    1   6  15  20  15   6   1
  1   7  21  35  35  21   7   1
1   8  28  56  70  56  28   8   1
```

Each number in the triangle is the sum of the two numbers directly above it. For example, in row 4:
- 1 is at the beginning of every row
- 3 = 1 + 2 (from row 3)
- 3 = 2 + 1 (from row 3)
- 1 is at the end of every row

This pattern shows how each row n is calculated from row n-1:
- The first and last elements of each row are always 1
- Each other element at position k is the sum of elements at positions k-1 and k from the previous row

### Formula

The binomial coefficient C(n,k) can be calculated using the formula:

C(n,k) = n! / (k! × (n-k)!)

Where n! represents the factorial of n.

### Recursive Definition

The binomial coefficient also has an elegant recursive definition:

C(n,k) = 1 if k = 0 or k = n
C(n,k) = C(n-1,k-1) + C(n-1,k) otherwise

## 8. Your Turn: Implementing Binomial Coefficients

Now it's your turn to implement functions for working with binomial coefficients in Clojure. Create a new file called `Binomial.clj` in the `src` directory and implement the following:

### 1. Calculate C(n,k)

Implement a function that calculates the binomial coefficient C(n,k):

```clojure
(defn binomial [n k]
  ;; Your implementation here
  )
```


### 2. Generate the nth Row of Pascal's Triangle

Implement a function that returns the entire nth row of Pascal's triangle as a sequence:

```clojure
(defn pascal-row [n]
  ;; Your implementation here
  )
```

For example:
- `(pascal-row 0)` should return `[1]`
- `(pascal-row 1)` should return `[1 1]`
- `(pascal-row 2)` should return `[1 2 1]`
- `(pascal-row 3)` should return `[1 3 3 1]`
- `(pascal-row 4)` should return `[1 4 6 4 1]`


Good luck, and have fun exploring Clojure's functional programming capabilities!

