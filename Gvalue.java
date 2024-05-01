package fifteenpuzzle;


import java.util.Stack;
public abstract class Gvalue{
    protected State firstState;
    protected State goalState;
    protected int numOfmove;

    public Gvalue(State firstState, State goalState) {
        this.firstState = firstState;
        this.goalState = goalState;
    }

    public abstract Stack<State> solve();

    public int getNumOfMove() {
        return numOfmove;
    }

}
