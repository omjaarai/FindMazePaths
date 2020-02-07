import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

    /**
     * Calls the parent class constructor.
     * 
     * @see Searcher
     * @param maze initial maze.
     */
    public AStarSearcher(Maze maze) {
        super(maze);
    }

    /**
     * Calculates the hStarValue and returns double value
     * 
     * @param square
     * @return hstarvalue
     */
    private double hStarValue(Square square) {
        // variable to store the hstar value
        double h;
        // calculate the hstar value
        h=Math.sqrt(Math.pow((square.X - maze.getGoalSquare().X), 2) + Math.pow((square.Y - maze.getGoalSquare().Y), 2));
        return h;
    }


    /**
     * Main a-star search algorithm.
     * 
     * @return true if the search finds a solution, false otherwise.
     */
    public boolean search() {
        // explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
        boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
        PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

        // initialize the root state and add to frontier list
        // calculate the hstar value for the first node
        double hVal = hStarValue(maze.getPlayerSquare());
        // add a state for the root
        State state = new State(maze.getPlayerSquare(), null, 0, 0 );
        // add FValue to the root 
        StateFValuePair stateFValuePair= new StateFValuePair(state, 0 + hVal);
        // add root to the frontier
        frontier.add(stateFValuePair);
        // loop to check if the frontier is not empty, if empty then no solutions
        while (!frontier.isEmpty()) {
            // update the maximum size of the frontier
            if(frontier.size() > this.maxSizeOfFrontier)
                maxSizeOfFrontier = frontier.size();
            // get the current StateFValue pair from the frontier
            StateFValuePair SFpair = frontier.poll(); 
            // get the current state from the StateFValuePair
            State currState = SFpair.getState();
            // increase number of nodes expanded every time node is polled from frontier
            noOfNodesExpanded++;
            // mark the node explored
            explored[currState.getX()][currState.getY()]=true;
            // if the current state is the goal state
            if(currState.isGoal(maze)) {
                // update the cost
                cost= currState.getGValue();
                // update the max depth
                maxDepthSearched=currState.getDepth();
                // replace the path with '.' character
                currState= currState.getParent();
                while(currState.getParent()!=null) {
                    maze.setOneSquare(currState.getSquare(), '.');
                    currState=currState.getParent();
                }
                // return true if a solution has been found
                return true;
            }
            // if currState is not the goal state
            else {
                // variable to check if the states in the frontier are equal to the successors
                boolean check= false;
                // iterator to get the successors of the maze
                Iterator<State> successors= currState.getSuccessors(explored, maze).iterator();
                // loop to check whether there are more successors the Arraylist
                while(successors.hasNext()) {
                    // get the next state
                    State n = successors.next();
                    // calculate the StateFValuePair of the next successor
                    double nextFValue= n.getGValue()+hStarValue(n.getSquare());
                    StateFValuePair SFNext= new StateFValuePair(n, nextFValue);
                    // if n is not explored
                    if(!explored[n.getX()][n.getY()]) {
                        // then mark n explored
                        explored[n.getX()][n.getY()]=true;
                        // loop to go through all the elements in the frontier
                        for(StateFValuePair j: frontier) {
                            // if the element is equal to n
                            if(j.getState().equals(n)) {
                                // mark check as true
                                check=true;
                            }
                            // if element is equal to n and the gValue of element is greater than n's gValue
                            if(j.getState().equals(n) && j.getState().getGValue()>n.getGValue()) {
                                // then add n's StateFValue to the frontier
                                frontier.add(SFNext);
                                // remove element from the frontier
                                frontier.remove(j);
                            }
                        }
                        // if check is false
                        if(!check) {
                            // add n's StateFValue to the frontier
                            frontier.add(SFNext);
                        }                       
                    }
                }
            }
        }
        // return false if no solution
        return false;       
    }
}
