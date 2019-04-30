package Heuristics;

import Models.LatinSquare;
import Models.Move;

import java.util.HashSet;

public class SimpleHeuristic implements Heuristic {
    @Override
    public Move chooseMove(LatinSquare level) {
        for (int i = 0; i < level.size; i++) {
            for (int j = 0; j < level.size; j++) {
                HashSet<Integer> domain = level.getDomain()[i][j];
                if(!domain.isEmpty()){
                    for (int k = 1; k <= level.size; k++) {
                        if(level.getDomain()[i][j].contains(k)){
                            return new Move(j,i,k);
                        }
                    }
                }
            }
        }
        return null;
    }
}
