package Heuristics;

import Models.LatinSquare;
import Models.Move;

public interface Heuristic {
    Move chooseMove(LatinSquare level);
}
