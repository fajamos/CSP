package Heuristics;

import Models.LatinSquare;
import Models.Move;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SmallestPowerHeuirstic implements Heuristic {
    @Override
    public Move chooseMove(LatinSquare level) {
        List<Cell> cellList = new ArrayList<>(level.size*level.size);
        for (int i = 0; i < level.size; i++) {
            for (int j = 0; j < level.size; j++) {
                int power = level.getDomain()[i][j].size();
                if(power>0) {
                    cellList.add(new Cell(j, i, power));
                }
            }
        }
        if(cellList.size()==0){
            return null;
        }
        cellList.sort(Comparator.comparing(Cell::getPower));
        int x = cellList.get(0).x;
        int y = cellList.get(0).y;
        return new Move(x,y,level.getDomain()[y][x].iterator().next());

    }

    private class Cell{
        int x;
        int y;
        int power;

        public int getPower() {
            return power;
        }

        public Cell(int x, int y, int power) {
            this.x = x;
            this.y = y;
            this.power = power;
        }
    }
}
