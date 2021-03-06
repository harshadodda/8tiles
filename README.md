# 8tiles
Summary:

This program lets the user play the 8 tiles game. The user can play the classic game by themselves or they can ask the 
program to see if there is a solution for the current board state. If there is a solution, the program will terminate after 
the solution is reached, if there is no solution, the program will terminate at the best possible board state. 
the player can set the starting board state and play from that state as well. The user can move tiles by clicking on a tile that is adjacent to the blank tile. The tile that was clicked on will then move to where the blank tile was. 

The auto complete algorithm gives the program control of the board and the algorithm figures out how to either solve the puzzle or it generates the best possible board state. This algorithm uses a heuristic to determine the best possible moves and executes those moves accordingly. The program then automatically goes through the moves that the algorithm took to get to the ending board state.

Here is a sample of the program running:

Step 1: Lets have the program set up a random board for us, initially. The initial board it gave us looks like this:

<img width="503" alt="screen shot 2017-06-21 at 4 39 11 pm" src="https://user-images.githubusercontent.com/13561051/27411281-8689505a-56a1-11e7-90b4-cad77fa26f94.png">

Step 2: Lets move a few tiles and see what happens. We will move the tiles in the following order: 3, 5, 6, 3. The screen shots will show the 4 moves in order. Each screen shot will show the board state before the move. The 5th screen shot will show the board state after the 4 moves.

<img width="503" alt="screen shot 2017-06-21 at 4 39 11 pm" src="https://user-images.githubusercontent.com/13561051/27411347-0cf8eb32-56a2-11e7-9608-4b57015716d0.png">

Step 3: First we move tile 3.

<img width="505" alt="screen shot 2017-06-21 at 4 39 27 pm" src="https://user-images.githubusercontent.com/13561051/27411353-1303cb82-56a2-11e7-938a-caf5dccd1a39.png">

Step 4: Then we move tile 5.

<img width="508" alt="screen shot 2017-06-21 at 4 39 36 pm" src="https://user-images.githubusercontent.com/13561051/27411357-182be2b6-56a2-11e7-8a6e-9471a5e44197.png">

Step 5: Next we move tile 6.

<img width="498" alt="screen shot 2017-06-21 at 4 39 44 pm" src="https://user-images.githubusercontent.com/13561051/27411360-1dd06eb2-56a2-11e7-90da-979d0acd9baf.png">

Step 6: Finally we move tile 3 again.

<img width="500" alt="screen shot 2017-06-21 at 4 39 53 pm" src="https://user-images.githubusercontent.com/13561051/27411364-28042108-56a2-11e7-9411-0e5f91449794.png">

Now let us manually configure a board and have the auto complete algorithm try to finish the game for us.

<img width="506" alt="screen shot 2017-06-21 at 4 40 10 pm" src="https://user-images.githubusercontent.com/13561051/27412019-aea6fde4-56a6-11e7-8783-2821e938ef1c.png">

This is the ending board state after using the automatic solver algorithm. It looks like the initial board we gave has a solution so the ending board is the numbers in order. Some boards do not have a solution and in these cases, the algorithm will display the best possible board state.

<img width="499" alt="screen shot 2017-06-21 at 4 42 32 pm" src="https://user-images.githubusercontent.com/13561051/27412025-be9e82a8-56a6-11e7-95e1-1bf34fa3e17b.png">

