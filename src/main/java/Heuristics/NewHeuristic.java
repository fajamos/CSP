package Heuristics;

import Models.LatinSquare;
import Models.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NewHeuristic implements Heuristic{

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
                    int minReps = size+1;
                    int maxValue = size+1;
                    int maxReps = 0;
                    for (int i : level.getDomain()[y][x]) {
                        if(row[y][i-1]<minReps){
                            minValue = i;
                            minReps = row[y][i-1];
                        }
                        if(column[x][i-1]<minReps){
                            minValue = i;
                            minReps = column[x][i-1];
                        }
                        if(row[y][i-1]>maxReps){
                            maxValue = i;
                            maxReps = row[y][i-1];
                        }
                        if(column[x][i-1]>maxReps){
                            maxValue = i;
                            maxReps = column[x][i-1];
                        }
                    }
                    cells.add(new Cell(x,y,minValue,minReps, maxValue, maxReps, level.getDomain()[y][x].size()));
                }
            }
        }
        if(cells.size()==0) return null;
        Cell best = Collections.min(cells);
        if(best == null || best.minReps ==size+1 || best.domainPower==0 || best.minValue == size+1){
            return null;
        }
        return best.getMove();
    }

    private class Cell implements Comparable<Cell>{
        final int x;
        final int y;
        final int minValue;
        final int minReps;
        final int maxValue;
        final int maxReps;
        final int domainPower;

        public Cell(int x, int y, int value, int reps, int maxValue, int maxReps, int domainPower) {
            this.x = x;
            this.y = y;
            this.minValue = value;
            this.minReps = reps;
            this.maxValue = maxValue;
            this.maxReps = maxReps;
            this.domainPower = domainPower;
        }


        public Move getMove(){
            return minReps == 1 ? new Move(x,y, minValue) : new Move(x,y,maxValue);
        }

        @Override
        public int compareTo(Cell o) {
            if(minReps==1 && o.minReps==1) return 0;
            if(minReps==1) return -1;
            if(o.minReps==1) return 1;
            int dom = Integer.compare(domainPower, o.domainPower);
            return dom == 0 ? Integer.compare(maxReps,o.maxReps) : dom;
        }
    }
}
