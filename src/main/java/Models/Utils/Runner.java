package Models.Utils;

import Heuristics.Heuristic;
import Models.Move;
import Models.Solution;
import Models.State;
import lombok.Getter;

import java.util.HashSet;

@Getter
public class Runner {
    private Heuristic heuristic;
    private State currentState;
    private HashSet<Solution> solutions = new HashSet<>();
    private long counterStepInto = 0;
    private long counterBacktracing = 0;
    private long firstSolutionAfter = 0;
    private State solutionState;
    private final boolean debug;

    public Runner(Heuristic heuristic, State currentState,boolean debug) {
        this.heuristic = heuristic;
        this.currentState = currentState;
        this.debug = debug;
    }

    public void run(){
        if(currentState.getLevel().forwardChecking){
            currentState.getLevel().deleteFromDomainInit();
        } else {
            currentState.getLevel().deleteFromDomainInitNoForwardChecking();
        }
        while(currentState.getParent()!=null || currentState.getLevel().isValid()){
            doMove();
        }
        System.out.printf("Moves: %d\nBacktraces: %d\nFirst Solution After: %d\n",counterStepInto,counterBacktracing,firstSolutionAfter);
        State state = solutionState;
        while(state!=null && debug){
            System.out.println(state.levelString());
            System.out.println();
            state = state.getParent();
        }
    }

    private void doMove(){
        if(counterBacktracing%500000==0){
            System.out.printf("Moves: %d\nBacktraces: %d\n",counterStepInto,counterBacktracing);
        }
        Move move = heuristic.chooseMove(currentState.getLevel());
        if(move==null){
            Solution solution = currentState.getLevel().checkForSulution();
            if(solution !=null){
                solutions.add(solution);
                solutionState = currentState;
                System.out.println(solutions.size());
                if(firstSolutionAfter==0){
                    firstSolutionAfter = counterStepInto;
                }
            }
            backtrace();
        } else {
            if (debug) System.out.println(currentState.levelString());
            counterStepInto++;
            currentState = new State(currentState, currentState.getLevel().doMove(move), move);
            while(currentState.getMove() !=null && !currentState.getLevel().isValid(currentState.getMove())){
                backtrace();
            }
        }
    }

    private void backtrace(){
        if (debug) {
            System.out.println("<backtrace>");
            System.out.println(currentState.levelString());
            System.out.println("</backtrace>");
        }
        counterBacktracing++;
        if (currentState.getParent() != null){
            currentState.getParent().getLevel().deleteFromDomainBacktrace(currentState.getMove());
        }
        currentState = currentState.getParent();
    }

}
