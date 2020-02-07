import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {
    

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}
	
	

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
	    // explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		// add a new state as the root
		State state= new State(maze.getPlayerSquare(), null, 0, 0);
		queue.add(state);
		// loop to check if the queue is not empty
		while (!queue.isEmpty()) {
			// store the current state in variable currState
		    State currState= queue.pop();
		    // update the number of nodes after popping out of the queue
		    noOfNodesExpanded ++;
		    // mark the node explored
		    explored[currState.getX()][currState.getY()] = true;
		    // check if the current state is the goal
		    if (currState.isGoal(maze)) {
		        // set the path to character '.'
                while(currState != null) {
                    if (!(currState.isGoal(maze) || currState.equals(state)))
                    maze.setOneSquare(currState.getSquare(), '.');
                    // update cost and depth
                    cost++;
                    maxDepthSearched++;
                    // update the current state
                    currState = currState.getParent();
                }
                // set the start back to S since it was replaced by '.'
                maze.setOneSquare(state.getSquare(), 'S');
                // update cost and depth since it counted the start node before
                cost --;
                maxDepthSearched --;
                return true;
            }
            // get all the successors and store them in Arraylist successors
            ArrayList <State> successors = new ArrayList<State>();
            successors = currState.getSuccessors(explored, maze);
            // loop to iterate through the successors
            for(State successor: successors) {
                // loop to iterate through the queue elements
                int i;
                for(i = 0; i < queue.size(); i++) {
                    // if i's X and Y are equal to successor's X and Y coordinates then break
                    if ((queue.get(i).getX() == successor.getX() && queue.get(i).getY() == successor.getY())) {
                        break;
                    }
                }
                // if the size of queue is equal to queue.size then add successor to the queue
                if ( queue.size() == i) {
                    queue.add(successor);
                }
            }
            // if the size of queue is greater than the max size of frontier then update maxSizeOfFrontier
            if (queue.size() > maxSizeOfFrontier) {
                maxSizeOfFrontier = queue.size();
            }            
        }
        // return false if no solution
        return false;
    }		
}
