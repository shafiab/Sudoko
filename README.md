Sudoko
======

Java code for solving Sudoko. The program reads a 9x9 unsolved Sudoko table from an input csv file, solves the puzzle and then write the table to an output csv file.

Algorithm
=========

The solver uses a backtracking algorithm. The main idea behind the algorithm is to determine an empty grid square, try out different possible digits. If for an empty square, a digit has no conflicts with the 20 peers of the square, the solver assigns the digit in that square. It then  moves to the next empty square and repeats the procedure. If for any square, all possible digits has conflict, the algorithm backtracks to previous empty square it tried. 

A peer of a square are the rows/colums and the 3x3 square (e.g. the 20 peers of C2 are shown below).
```

    A2   |         |        |         |         |         |     A1 A2 A3|         |         
    B2   |         |        |         |         |         |   B1 B2 B3|         |         
    C2   |         |        | C1 C2 C3| C4 C5 C6| C7 C8 C9|   C1 C2 C3|         |         
---------|---------|---------  ---------|---------|---------  ---------|---------|---------

    D2   |         |                    |         |                    |         |         
    E2   |         |                    |         |                    |         |         
    F2   |         |                    |         |                    |         |         
---------+---------+---------  ---------+---------+---------  ---------+---------+---------

    G2   |         |                    |         |                    |         |         
    H2   |         |                    |         |                    |         |         
    I2   |         |                    |         |                    |         |         
    ```

A very fast and elegant algorithm to solve sudoko using constraint propagation and backtracking is available in Peter Norvig's website:
http://norvig.com/sudoku.html

However, my java code does not make use of the above mentioned constraints propagation techniques. It is just a simple backtracking algorithm. In order to improve the efficiency of the backtracking, I took a few idea from Norvig's website and modified the algorithm for selecting the empty squares.

Initial Steps
=============

Before running the backtracking algorithm, we figure out all possible digits for each of the empty squares. For example, for the following table,

```
4 0 0 |0 0 0 |8 0 5 
0 3 0 |0 0 0 |0 0 0 
0 0 0 |7 0 0 |0 0 0 

------+------+------
0 2 0 |0 0 0 |0 6 0 
0 0 0 |0 8 0 |4 0 0 
0 0 0 |0 1 0 |0 0 0 

------+------+------
0 0 0 |6 0 3 |0 7 0 
5 0 0 |2 0 0 |0 0 0 
1 0 4 |0 0 0 |0 0 0 
```

We first figure out all possible digits for each of the empty square as follows:

  -      1679   12679   |  139     2369    269   |   -      1239     -    
 26789     -    1256789 | 14589   24569   245689 | 12679    1249   124679 
  2689   15689   125689 |   -     234569  245689 | 12369   12349   123469 
------------------------+------------------------+------------------------

  3789     -     15789  |  3459   34579    4579  | 13579     -     13789  
  3679   15679   15679  |  359      -     25679  |   -     12359   12379  
 36789     -     56789  |  359      -     25679  | 23579   23589   23789  
------------------------+------------------------+------------------------

  289      89     289   |   -      459      -    |  1259     -     12489  
   -      6789     3    |   -      479      -    |   69     489     4689  
   -      6789     4    |  589     579     5789  | 23569   23589   23689  

   
Now, if only one digit is possible for an empty square, we just assign the digit to that square. Now for searching for an empty square, the traditional algorithm tries the order it appears in the grid. However, similar to Norvig's tutorial, I use a common heuristic called **minimum remaining values**. E.g. suppose, we have for empty squares in a grid and the number of possible digits in the squares are 7, 2, 5, 3, a total of 7x2x5x3 = 210 combination. Now if we choose to solve the first square, then there are  7 possibilities, so we'd expect to guess wrong with probability 6/7. If instead we choose the second square, which only has 2 possibilities, we'd expect to be wrong with probability only 1/2. Thus we choose the square with the fewest possibilities and the best chance of guessing right. Therefore, we will try the squares with 2, 3, 5 and 7 possibilities respectively.
