package sample;
/**
 * The node class holds the board and its parent so the nodes together create a tree. When we find the
 * best heuristic board or the board that is finished, we can simply follow the last node's parent pointer
 * up and find the path we took
 */
public class Node {

    private Board board;
    private Node parent;

    /**
     * @return the parent for this node
     */
    public Node getParent() {
        return this.parent;
    }

    /**
     * sets the parent for this node
     * @param newParent, the parent for this node
     */
    public void setParent(Node newParent) {
        this.parent = newParent;
    }

    /**
     * @return the board for this node
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * sets the board for this node
     * @param newBoard, the board for this node
     */
    public void setBoard(Board newBoard) {
        this.board = newBoard;
    }

    /**
     * prints the board for this node
     */
    public void printNode() {
        System.out.println("node board:");
        board.printBoard();
    }

    /**
     * the constructor sets the board and parent for this node instance
     * @param newBoard, the board for this node instance
     * @param newParent, the parent for this node instance
     */
    public Node(Board newBoard, Node newParent) {
        this.board = newBoard;
        this.parent = newParent;
    }



}

