package fifteenpuzzle;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class AStar extends Gvalue{

    public AStar(State firstState, State goalState) {
        super(firstState,goalState);
    }
    @Override
    public Stack<State> solve() {
        Stack<State> ret = new Stack<>();
        PriorityQueue<State> open = new PriorityQueue<State>(10000, new Compare());
        Set<State> closed = new HashSet<State>();
        StateExpand generator = new StateExpand();

        open.add(firstState);
        closed.add(firstState);
        while (!open.isEmpty()) {
            State current = open.poll();
            if (current.equals(goalState)) {
                this.numOfmove = current.getGval();
                ret = current.getPath();
                return ret;
            }
            List<State> children = generator.expandStates(current);
            for (State state : children) {
                state.sethValue(goalState);
                if (!closed.contains(state)) {
                	open.add(state);
                	closed.add(state);
                }
            }
        }
        return ret;
    }
}
