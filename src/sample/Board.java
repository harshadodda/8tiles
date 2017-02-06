package sample;
import java.util.Random;

/**
 * The board class has everything that a single board needs to calculate
 * its heuristic value, its hash value, and initialization
 */
public class Board {
    private int[][] board = new int[3][3]; // the current board instance
    private int[] initializeArray = new int[9]; // the initialisation array, used to make the board randomly
    private int[][] solvedBoard = new int[3][3]; // an instance of the solved board

    /**
     * Copy constructor
     * @param b1, the board to be copied
     */
    public Board(Board b1) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.board[i][j] = b1.board[i][j];
            }
        }
    }

    /**
     * The empty constructor
     * We need this because if you override the given empty constructor and want to use it,
     * you must provide it yourself. I overrode the constructor given so I need to have this
     */
    public Board() {

    }

    /**
     * sets the current instance of the board
     * @param newBoard, the board to be set
     */
    public void setBoard(int[][] newBoard) {
        this.board = newBoard;
    }

    /**
     * gets the current instance of the board
     * @return the current instance of the board
     */
    public int[][] getBoard() {
        return this.board;
    }

    /*
    public String swapvals(String board, int choice) {

        String retString = "empty";
        boolean validMove = false;
        Board retBoard = initializeBoardManually(board); // set the board as it is shown
        validMove = isNextTo0(choice);
        if(validMove) {
            retBoard.swap(0, choice);
            retString = retBoard.toString();
        }
        else {
            retString = ""
        }
        return retString;
     */




    /**
     * initializes the solved board
     */
    public void initializeSolvedBoard() {
        int counter = 1;
        for(int i = 0; i < this.solvedBoard.length; i++) {
            for (int j = 0; j < this.solvedBoard[i].length; j++) {
                if (i == 2 && j == 2) {
                    this.solvedBoard[i][j] = 0;
                } else {
                    this.solvedBoard[i][j] = counter;
                    counter++;
                }
            }
        }
    }

    /**
     * check if the array for initalization is empty, signaling that the intialization is complete
     * @return true if initialzation is done, false if it is not
     */
    public boolean isInitializeArrayEmpty() {
        boolean isEmpty = true;
        for(int i = 0; i < this.initializeArray.length; i++) {
            if(this.initializeArray[i] != -1) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    /**
     * randomly initializes the board
     */
    public void initalizeBoardRandomly() {
        initializeSolvedBoard();
        boolean isEmpty = false;
        int row = 0;
        int col = 0;

        for(int i = 0; i < 9; i++) {
            this.initializeArray[i] = i;
        }

        while(!isEmpty) {
            Random randomNum = new Random();
            randomNum.setSeed(System.currentTimeMillis()); // seed the random number generator
            int random = randomNum.nextInt(9);
            if(this.initializeArray[random] != -1) {
                this.board[row][col] = random;
                this.initializeArray[random] = -1;
                col++;
                if(col > 2) {
                    row++;
                    col = 0;
                }
                isEmpty = this.isInitializeArrayEmpty(); // check if initialization is done
            }
        }
    }

    /**
     * initializes the board based on string passed in. We assume the string passed in is correct
     * @param boardValues, the string used to initialize the board
     * @return the initialized board
     */
    public Board initializeBoardManually(String boardValues) {
        initializeSolvedBoard(); // initializes the solved board
        char[] input;
        input = boardValues.toCharArray();
        int col = 0;
        int row = 0;
        for(int i = 0; i < input.length; i++) {
            int tempNum = Character.getNumericValue(input[i]); // gets the characters in order
            this.board[row][col] = tempNum;
            col++;
            if(col > 2) {
                row++;
                col = 0;
            }
        }
        return this;
    }

    /**
     * finds the heuristic value of a board by using the city block method
     * @return returns the heuristic value of a given board
     */
    public int findHeuristic() {
        // initializes the solved array
        int[] tempArray = new int[10];
        for (int i=1; i<10; i++)	{
            tempArray[i] = i;
        }
        tempArray[9] = 0; // add last value, the space, to the solved array
        int yCoordinateDifference;
        int xCoordinateDifference;
        int heuristic = 0;
        for (int i=0; i<3; i++)	{
            for (int j=0; j<3; j++)	{
                // get the difference from this board to solved board in both x and y coordinates
                yCoordinateDifference = Math.abs(i - findYCoordinate(tempArray[(3*i)+j+1]));
                xCoordinateDifference = Math.abs(j - findXCoordinate(tempArray[(3*i)+j+1]));
                // add the difference to heuristic value
                heuristic += xCoordinateDifference;
                heuristic += yCoordinateDifference;
            }
        }
        return heuristic;
    }

    /**
     * Finds the X coordinate in the board for a given value
     * @param val, the value whos x coord you want
     * @return the x coord of the value you want
     */
    private int findXCoordinate(int val)	{
        int xCoord = 0;
        for (int i=0; i<3; i++)	{
            for (int j=0; j<3; j++)	{
                if (this.board[i][j] == val)	{
                    xCoord = j;
                }
            }
        }
        return xCoord;
    }

    /**
     * Finds the Y coordinate in the board for a given value
     * @param val, the value whos y coord you want
     * @return the y coord of the value you want
     */
    private int findYCoordinate(int val)	{
        int yCoord = 0;
        for (int i=0; i<3; i++)	{
            for (int j=0; j<3; j++)	{
                if (this.board[i][j] == val)	{
                    yCoord = i;
                }
            }
        }
        return yCoord;
    }

    /**
     * Prints the current board instance
     */
    public void printBoard() {
        for(int i = 0; i < this.board.length; i++) {
            System.out.print("  ");
            for(int j = 0; j < this.board[i].length; j++) {
                if(this.board[i][j] != 0) {
                    System.out.print(this.board[i][j] + " ");
                }
                else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    /**
     * finds the xcoord of a given board value, used in the swap function
     * @param numToFind, the num you want the x coord of
     * @return the x coord of the value you want
     */
    private int findxcoord(int numToFind) {
        int retVal = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(this.board[i][j] == numToFind) {
                    retVal = i;
                }
            }
        }
        return retVal;
    }

    /**
     * finds the ycoord of a given board value, used in swap function
     * @param numToFind, the num you want the y coord of
     * @return the y coord of the value you want
     */
    private int findycoord(int numToFind) {
        int retVal = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(this.board[i][j] == numToFind) {
                    retVal = j;
                }
            }
        }
        return retVal;
    }

    /**
     * swaps the two values in the current board instance
     * @param val1, the first value to swap
     * @param val2, the second value to swap
     */
    public void swap(int val1, int val2) {
        int val1x, val1y, val2x, val2y;
        // the coordinates of the values
        val1x = Math.abs(findxcoord(val1));
        val1y = Math.abs(findycoord(val1));
        val2x = Math.abs(findxcoord(val2));
        val2y = Math.abs(findycoord(val2));
        // swap the values
        this.board[val1x][val1y] = val2;
        this.board[val2x][val2y] = val1;
    }

    /**
     * checks if the value is next to 0 on the board instance
     * @param val, the value you want to check
     * @return true if the value is next to the 0 or blank space, false if not
     */
    public boolean isNextTo0(int val) {
        int x0, y0, xval, yval;
        // find coords of 0 and value
        x0 = Math.abs(findxcoord(0));
        y0 = Math.abs(findycoord(0));
        xval = Math.abs(findxcoord(val));
        yval = Math.abs(findycoord(val));
        if(((Math.abs(x0 - xval) <= 1) && (Math.abs(y0 - yval) <= 0))
                || ((Math.abs(x0 - xval) <= 0) && (Math.abs(y0 - yval) <= 1))) {
            return true;
        }
        return false;
    }

    /**
     * finds the number of next possible board moves depending on where the space or 0 is
     * @return the number of moves possible based on where the blank tile is
     */
    public int numMovesPossible() {
        int numMovesPossible = 0;
        int x = findxcoord(0);
        int y = findycoord(0);
        if((x == 0 && y == 0) || (x == 2 && y == 0) || (x == 0 && y == 2) || (x == 2 && y == 2)) {
            numMovesPossible = 2; // corners have 2 possible moves
        }
        else if((x == 1 && y == 0) || (x == 0 && y == 1) || (x == 2 && y == 1) || (x == 1 && y == 2)) {
            numMovesPossible = 3; // the edges have 3 possible moves
        }
        else if((x == 1 && y == 1)) {
            numMovesPossible = 4; // only the middle has 4 possible moves
        }
        return numMovesPossible;
    }

    /**
     * finds the moves possible for a given board
     * @return an array showing which numbers can be moved
     */
    public int[] movesPossible() {
        int num = numMovesPossible();
        int[] moves = new int[num];
        int x = findxcoord(0);
        int y = findycoord(0);
        // 4 moves possible for the 0, some not on baord
        int[] xmoves = new int[4];
        int[] ymoves = new int[4];
        // left
        xmoves[0] = x - 1;
        ymoves[0] = y;
        // right
        xmoves[1] = x + 1;
        ymoves[1] = y;
        // top
        xmoves[2] = x;
        ymoves[2] = y - 1;
        // bottom
        xmoves[3] = x;
        ymoves[3] = y + 1;
        int counter = 0;
        for(int i = 0; i < 4; i++) { // check whether the move is valid and if so, add it to the array
            if((xmoves[i] >= 0 && xmoves[i] <= 2) && (ymoves[i] >= 0 && ymoves[i] <= 2)) {
                if(this.board[xmoves[i]][ymoves[i]] != 0) {
                    moves[counter] = this.board[xmoves[i]][ymoves[i]];
                    counter++;
                }
            }
        }
        return moves;
    }

    /**
     * checks if the current board is the solved board
     * @return true if the current board is the solved board, false if not
     */
    public boolean isSolved() {
        boolean solved = true;
        for(int i = 0; i < this.board.length; i++) {
            for(int j = 0; j < this.board[i].length; j++) {
                if(this.board[i][j] != this.solvedBoard[i][j]) {
                    solved = false;
                }
            }
        }
        return solved;
    }

    /**
     * takes a board and gives a unique string that denotes its hash, used for hash table
     * @return a unique string that represents the order of the board
     */
    public String toString() {
        String hash = "";
        for(int i = 0; i < this.board.length; i++) {
            for(int j = 0; j < this.board[i].length; j++) {
                String tempStr = String.valueOf(this.board[i][j]);
                hash += tempStr;
            }
        }
        return hash;
    }

}
