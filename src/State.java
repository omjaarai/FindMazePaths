import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

    private Square square;
    private State parent;

    // Maintain the gValue (the distance from start)
    // You may not need it for the BFS but you will
    // definitely need it for AStar
    private int gValue;

    // States are nodes in the search tree, therefore each has a depth.
    private int depth;

    /**
     * @param square
     *            current square
     * @param parent
     *            parent state
     * @param gValue
     *            total distance from start
     */
    public State(Square square, State parent, int gValue, int depth) {
        this.square = square;
        this.parent = parent;
        this.gValue = gValue;
        this.depth = depth;
    }

    /**
     * @param visited
     *            explored[i][j] is true if (i,j) is already explored
     * @param maze
     *            initial maze to get find the neighbors
     * @return all the successors of the current state
     */
    public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
        // Get the current state and store it in the currState variable
        State currState= new State(square, parent, gValue, depth);
        //ArrayList to store all the successors
        ArrayList<State> successors = new ArrayList<>();
        //Variables to get the coordinates of current state
        int X= currState.getX();
        int Y= currState.getY();
        
        //check if the left neighbor is not explored
        if(!explored[X][Y-1]) {
            //check if there is free path
            if(maze.getSquareValue(X, Y-1)!='%') {
                //update the path and add it to the successors list
                Square leftSquare = new Square(X,Y-1);
                State leftState= new State(leftSquare, currState, gValue+1, depth +1);
                successors.add(leftState);
            }
        }
        //check if the bottom neighbor is not explored
        if(!explored[X+1][Y]) {
            //check if there is free path
            if(maze.getSquareValue(X+1, Y)!='%') {
                //update the path and add it to the successors list
                Square downSquare = new Square(X+1,Y);
                State downState= new State(downSquare, currState, gValue+1, depth +1);
                successors.add(downState);
            }
        }
        //check if the right neighbor is not explored
        if(!explored[X][Y+1]) {
            //check if there is free path
            if(maze.getSquareValue(X, Y+1)!='%') {
                //update path and add it to the list
                Square rightSquare = new Square(X,Y+1);
                State rightState= new State(rightSquare, currState, gValue+1, depth +1);
                successors.add(rightState);
            }
        }
        //check if the top neighbor is not explored
        if(!explored[X-1][Y]) {
            //check if there is a free path
            if(maze.getSquareValue(X-1, Y)!='%') {
                //update the path and add it to the list
                Square topSquare = new Square(X-1,Y);
                State topState= new State(topSquare, currState, gValue+1, depth +1);
                successors.add(topState);
            }
        }
        return successors;
    }

    /**
     * @return x coordinate of the current state
     */
    public int getX() {
        return square.X;
    }

    /**
     * @return y coordinate of the current state
     */
    public int getY() {
        return square.Y;
    }

    /**
     * @param maze initial maze
     * @return true is the current state is a goal state
     */
    public boolean isGoal(Maze maze) {
        if (square.X == maze.getGoalSquare().X
            && square.Y == maze.getGoalSquare().Y)
            return true;

        return false;
    }

    /**
     * @return the current state's square representation
     */
    public Square getSquare() {
        return square;
    }

    /**
     * @return parent of the current state
     */
    public State getParent() {
        return parent;
    }

    /**
     * You may not need g() value in the BFS but you will need it in A-star
     * search.
     * 
     * @return g() value of the current state
     */
    public int getGValue() {
        return gValue;
    }

    /**
     * @return depth of the state (node)
     */
    public int getDepth() {
        return depth;
    }
}
