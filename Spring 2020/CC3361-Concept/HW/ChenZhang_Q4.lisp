(defun max1 (a b c)
(if (> (if (> a b) a b) c) (if (> a b) a b) c))
(princ "largest number is: ")
(write(max1 12 5 7))

(defun foo(obj L)
(if(zerop (length L))
nil
(if(equal(car L)obj)
T
(foo obj (cdr L)))))
(print(foo 1 '(1 2 3)))
(print(foo 6 '(1 2 3)))