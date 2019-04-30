package Models.Futoshiki;

import java.util.Arrays;

public class FutoCoinstraintTable {
    private int[][] horizontalTable;
    private int[][] verticalTable;
    private int size;
    public FutoCoinstraintTable(int size){
        this.size = size;
        horizontalTable = new int[size][size-1];
        verticalTable = new int[size-1][size];
        for(int[] row : horizontalTable){
            Arrays.fill(row,0);
        }
        for(int[] row : verticalTable){
            Arrays.fill(row,0);
        }
    }

    public void addCoinstraint(int x1, int y1, int x2, int y2){
        if(Math.abs(x1-x2) + Math.abs(y1-y2) != 1){
            throw new IllegalArgumentException("Coinstraint should have either same x or y ");
        }
        if(Math.abs(x1-x2) == 1){
            addHorizontalCoinstraint(x1, x2, y1);
        }
        if(Math.abs(y1-y2) == 1){
            addVerticalCoinstraint(y1, y2, x1);
        }
    }

    private void addHorizontalCoinstraint(int x1, int x2, int y) {
        horizontalTable[y][Math.min(x1,x2)] = x1-x2;
    }

    private void addVerticalCoinstraint(int y1, int y2, int x) {
        verticalTable[Math.min(y1,y2)][x] = y1-y2;
    }

    public int getCoinstraint(int x1, int y1, int x2, int y2){ // c1 < c2 -> -1
        if(x1-x2 == 0){ //vertical
            return verticalTable[Math.min(y1,y2)][x1] * (y2-y1);

        } else{ //horizontal
            return horizontalTable[y1][Math.min(x1,x2)] * (x2-x1);
        }
    }

    @Override
    public String toString() {
        String result = "Horizotnal:\n";
        for(int[] row : horizontalTable){
            for (int i : row){
                result += i + " ";
            }
            result+="\n";
        }
        result += "\nVertical:\n";
        for(int[] row : verticalTable){
            for (int i : row){
                result += i + " ";
            }
            result+="\n";
        }
        result += "\n\n";
        return result;
    }
}
