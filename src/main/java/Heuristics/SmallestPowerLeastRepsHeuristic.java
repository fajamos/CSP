package Heuristics;

import Models.LatinSquare;
import Models.Move;

import java.util.*;

public class SmallestPowerLeastRepsHeuristic implements Heuristic{

    private ArrayList<Cell> cells;

    @Override
    public Move chooseMove(LatinSquare level) {
        int size = level.size;
        int[][] column = new int[size][size];
        int[][] row = new int[size][size];
        cells = new ArrayList<>(size*size);
        for (int i = 0; i < size; i++) {
            Arrays.fill(column[i],0);
            Arrays.fill(row[i],0);
        }
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                for (int d : level.getDomain()[y][x]){
                  row[y][d-1]++;
                  column[x][d-1]++;
              }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int value = 0; value < size; value++) {
                if(row[i][value]==0) row[i][value] = size+1;
                if(column[i][value]==0) column[i][value] = size+1;
            }
        }
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if(level.table[y][x]==0){
                    int minValue = size+1;
                    int reps = size+1;
                    for (int i : level.getDomain()[y][x]) {
                        if(row[y][i-1]<reps){
                            minValue = i;
                            reps = row[y][i-1];
                        }
                        if(column[x][i-1]<reps){
                            minValue = i;
                            reps = column[x][i-1];
                        }
                    }
                    cells.add(new Cell(x,y,minValue,reps,level.getDomain()[y][x].size()));
                }
            }
        }
        if(cells.size()==0) return null;
        Cell best = Collections.min(cells);
        if(best == null || best.reps==size+1 || best.domainPower==0 || best.value == size+1){
            return null;
        }
        return best.getMove();
    }

    private class Cell implements Comparable<Cell>{
        final int x;
        final int y;
        final int value;
        final int reps;
        final int domainPower;

        public Cell(int x, int y, int value, int reps, int domainPower) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.reps = reps;
            this.domainPower = domainPower;
        }


        public Move getMove(){
            return new Move(x,y,value);
        }

        @Override
        public int compareTo(Cell o) {
            int dom = Integer.compare(domainPower, o.domainPower);
            return dom == 0 ? Integer.compare(reps,o.reps) : dom;
        }
    }
}
