package sample;

import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * This class contains the priority queue which helps us find the best path
 * that takes us to the best heuristic value. We use the java priority queue
 * as a blackbox. I just passed in a comparator function to tell java that it
 * is a min heap rather than a max heap
 *
 */
public class SearchTree {
    int initialCapacity = 10; // starting capacity
    public PriorityQueue<Node> queue = new PriorityQueue<Node>(initialCapacity, new Comparator<Node>() {
        // comparator function, makes the pq a min heap
        public int compare(Node node1, Node node2) {
            if (node1.getBoard().findHeuristic() < node2.getBoard().findHeuristic())
                return 1;
            else if (node1.getBoard().findHeuristic() > node2.getBoard().findHeuristic())
                return 0;
            return 0;
        }
    });
}


