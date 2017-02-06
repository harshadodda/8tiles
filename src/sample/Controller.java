package sample;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.util.Duration;


import java.util.HashMap;

public class Controller {

    /**
     * Empty constructor
     */
    public Controller() {

    }

    // Initialize buttons and variables, link buttons to FXML document
    @FXML private Button b1;
    @FXML private Button b2;
    @FXML private Button b3;
    @FXML private Button b4;
    @FXML private Button b5;
    @FXML private Button b6;
    @FXML private Button b7;
    @FXML private Button b8;
    @FXML private Button b9;
    private boolean isBoardSet = false;
    private Button[][] buttonArray = new Button[3][3];
    private Board myBoard = new Board();
    private int counter = 0;
    private PauseTransition[] pArr;
    private int indexPArr = 0;

    /**
     * Initializes the board randomly
     */
    @FXML public void initialize() {
        buttonArray[0][0] = b1;
        buttonArray[0][1] = b2;
        buttonArray[0][2] = b3;
        buttonArray[1][0] = b4;
        buttonArray[1][1] = b5;
        buttonArray[1][2] = b6;
        buttonArray[2][0] = b7;
        buttonArray[2][1] = b8;
        buttonArray[2][2] = b9;
        myBoard.initalizeBoardRandomly();
        int[][] tempBoard = myBoard.getBoard();

        for(int i = 0; i < tempBoard.length; i++) {
            for(int j = 0; j < tempBoard[i].length; j++) {
                if(tempBoard[i][j] != 0) { // if the board is 0, we leave it blank
                    buttonArray[i][j].setText(Integer.toString(tempBoard[i][j])); // sets the button text
                }
            }
        }
    }

    /**
     * Sets the board if it hasnt been set, or swaps the values otherwise
     * @param event, sends the clicker of this object into the function
     */
    @FXML public void gridButtonHandler(ActionEvent event) {
        if(isBoardSet) {
            initiateBoard(event); //initializes the board
            counter++;
            if(counter > 8) {
                counter = 0;
                isBoardSet = false; // finishes setting the board
            }
        }
        else {
            swap(event); // swaps values if it is a valid move
        }
    }

    /**
     * Resets the board if the set board button is clicked then lets user set the board
     */
    @FXML public void setBoardHandler() {
        resetBoard(); // resets the board
        isBoardSet = true; // board is now set
        counter = 0; // counter is 0 for next time the user wants to set the board
    }

    /**
     * exits the program if the exit button is pressed
     */
    @FXML public void exitHandler() {
        System.exit(0);
    }

    /**
     * Resets the board
     */
    private void resetBoard() {
        int[][] tempBoard = myBoard.getBoard();
        for(int i = 0; i < tempBoard.length; i++)    {
            for(int j = 0; j < tempBoard[i].length; j++)   {
                buttonArray[i][j].setText(""); // makes the board blank
                tempBoard[i][j] = 0;
            }
        }
        myBoard.setBoard(tempBoard); // replaces the old board
    }

    /**
     * Initiates the board by having the user select the order of the tiles, starting at 0 or the blank tile
     * @param event, the button that is clicked
     */
    private void initiateBoard(ActionEvent event) {
        int[][] tempBoard = myBoard.getBoard();

        for(int i = 0; i < tempBoard.length; i++) {
            for(int j = 0; j < tempBoard[i].length; j++) {
                if(event.getSource().equals(buttonArray[i][j])) {
                    if(counter == 0) {
                        tempBoard[i][j] = 0;
                        buttonArray[i][j].setText("");
                    }
                    else {
                        tempBoard[i][j] = counter;
                        buttonArray[i][j].setText(Integer.toString(counter));
                    }
                }
            }
        }
        myBoard.setBoard(tempBoard);
    }

    /**
     * Swaps the tile with the blank tile, otherwise if the user clicked a tile not next to 0(blank) or the blank
     * itself, it does nothing
     * @param event, the button that is clicked
     */
    private void swap(ActionEvent event) {
        int xCoord;
        int yCoord;
        xCoord = getXCoordOfBlank();
        yCoord = getYCoordOfBlank();
        for(int i = 0; i < myBoard.getBoard().length; i++) {
            for (int j = 0; j < myBoard.getBoard()[i].length; j++) {
                if (event.getSource().equals(buttonArray[i][j])) {
                    if (i == xCoord && j == yCoord) {
                        break;
                    }
                    if (myBoard.isNextTo0(Integer.parseInt(buttonArray[i][j].getText()))) {
                        myBoard.swap(Integer.parseInt(buttonArray[i][j].getText()), 0);
                        buttonArray[xCoord][yCoord].setText(buttonArray[i][j].getText());
                        buttonArray[i][j].setText("");
                    }
                }
            }
        }

    }

    /**
     * Gets the x coordinate of the blank space
     * @return the x coord of blank space
     */
    private int getXCoordOfBlank() {
        int x = 0;
        for(int i = 0; i < myBoard.getBoard().length; i++) {
            for(int j = 0; j < myBoard.getBoard()[i].length; j++) {
                if(buttonArray[i][j].getText().equals(""))   {
                    x = i;
                    break;
                }
            }
        }
        return x;
    }

    /**
     * Gets the y coordinate of the blank space
     * @return the y coord of blank space
     */
    private int getYCoordOfBlank() {
        int y = 0;
        for(int i = 0; i < myBoard.getBoard().length; i++) {
            for(int j = 0; j < myBoard.getBoard()[i].length; j++) {
                if(buttonArray[i][j].getText().equals(""))   {
                    y = j;
                    break;
                }
            }
        }
        return y;
    }

    /**
     * This handles the solve button to finish the board for the user
     */
    public void solveHandler() {
        Node removedNode = finishAutomatically(myBoard);
        int moveCounter = recursiveCounter(removedNode);
        this.pArr = new PauseTransition[moveCounter];
        setGUI(removedNode);
        SequentialTransition sTrans = new SequentialTransition(pArr);
        sTrans.play();
    }

    /**
     * finishes the game by solving as much as possible by stopping at the best heuristic possible
     * @param myBoard, the current board
     */
    private Node finishAutomatically(Board myBoard) {
        SearchTree mypq = new SearchTree(); // initialize the priority queue
        HashMap<String, Node> hmap = new HashMap<String, Node>(); // initialize the hash map
        Node firstNode = new Node(myBoard, null); // first node has no parent
        mypq.queue.add(firstNode); // add the node to pq
        hmap.put(myBoard.toString(), firstNode); // add node to hash map
        Node lowestSoFar = new Node(myBoard, null); // stores the lowest heuristic value so far
        Node removedNode;
        while(!(mypq.queue.isEmpty())) {
            removedNode = mypq.queue.remove(); // remove lowest heuristic value
            if (removedNode.getBoard().findHeuristic() < lowestSoFar.getBoard().findHeuristic()) {
                // update the lowest node seen so far
                lowestSoFar = removedNode;
            }
            if (removedNode.getBoard().findHeuristic() == 0) { // if the board has value 0, we are done
                break;
            }
            generateChildren(hmap, mypq, removedNode); // generate the children of this node
        }
        return lowestSoFar;
    }

    /**
     * sets the GUI for each step in the solution
     * @param endNode, the last node in the solution
     */
    private void setGUI(Node endNode) {
        if(endNode == null) {
            return;
        }
        setGUI(endNode.getParent());
        pauseTrans(endNode);
    }

    /**
     * Takes the board and prints it to the GUI
     * @param tempBoard, the board to print in the GUI
     */
    private void boardToGrid(Board tempBoard) {
        for(int i = 0; i < tempBoard.getBoard().length; i++) {
            for(int j = 0; j < tempBoard.getBoard()[i].length; j++) {
                buttonArray[i][j].setText(Integer.toString(tempBoard.getBoard()[i][j]));
                if(tempBoard.getBoard()[i][j] == 0) {
                    buttonArray[i][j].setText("");
                }
            }
        }
    }

    /**
     * Pauses the execution of the GUI display by 300ms
     * @param currentNode, the current node in the solution tree
     */
    private void pauseTrans(Node currentNode) {
        pArr[indexPArr] = new PauseTransition(Duration.millis(300));
        pArr[indexPArr].setOnFinished(event -> boardToGrid(currentNode.getBoard()));
        indexPArr++;
    }

    /**
     * counts how many moves the automatic solve took
     * @param endNode, the last node of the best path
     */
    private static int recursiveCounter(Node endNode) {
        int numMoves = 0;
        while(endNode != null) {
            numMoves++;
            endNode = endNode.getParent();
        }
        return numMoves;
    }

    /**
     * generates all the children of a node and attaches them to their parent.
     * Also puts the node into the hash map and priority queue if it has never
     * been seen before
     * @param hmap, the running hash map of unique nodes
     * @param mypq, the priority queue of all unique unvisited nodes
     * @param removedNode, the node whose children need to be generated
     */
    private static void generateChildren(HashMap<String, Node> hmap, SearchTree mypq, Node removedNode) {
        int numMovesPossible = removedNode.getBoard().numMovesPossible(); // find the num of moves possible from current board
        int[] movesPossible = removedNode.getBoard().movesPossible(); // generate all the possible moves into an array
        for(int i = 0; i < numMovesPossible; i++) { // iterate through the possible boards and add them to hash map and pq if necessary
            Board childBoard = new Board(removedNode.getBoard());
            childBoard.swap(0, movesPossible[i]);
            if(!(hmap.containsKey(childBoard.toString()))) {
                Node tempNode = new Node(childBoard, removedNode);
                mypq.queue.add(tempNode);
                hmap.put(childBoard.toString(), tempNode);
            }
        }
    }
}
