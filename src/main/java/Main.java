import Heuristics.NewHeuristic;
import Heuristics.SimpleHeuristic;
import Heuristics.SmallestPowerHeuirstic;
import Heuristics.SmallestPowerLeastRepsHeuristic;
import Models.State;
import Models.Solution;
import Models.Utils.*;

import java.io.IOException;

public class Main {
    static long startTime;
    static long endTime;
    public static void main(String[] args) throws IOException {
        FileLoader fileLoader = new FutoshikiLoader();
        State root = new State(null, fileLoader.loadFile(9, 0,true), null);
        Runner runner = new Runner(new SmallestPowerHeuirstic(), root,false);
        startTime = System.nanoTime();
        runner.run();
        endTime = System.nanoTime();
        for(Solution solution : runner.getSolutions()){
            System.out.println(solution + "\n");

        }
        System.out.println("Miliseconds: " + (endTime-startTime)/1000000);
    }
}
