package fifteenpuzzle;

import java.util.Comparator;

public class Compare implements Comparator<State> {

    @Override
    public int compare(State s1, State s2) {
        if (s1.gethValue() < (s2.gethValue())){
        	return -1;
        }
        if (s1.gethValue() > (s2.gethValue())){
        	return 1;
        }
        
        return 0;
    }
}
