(defun towers-of-hanoi (n)   (transfer 'A 'B 'C n))  (defun move-disk (from to)   (list (list 'MOVE 'DISK 'FROM from 'TO to)))  (defun transfer (from to spare n)   (cond ((equal n 1)   (move-disk from to))  (t (append         (transfer from spare to (- n 1))      (move-disk from to)      (transfer spare to from (- n 1))))))