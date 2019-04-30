package Models.Futoshiki;

import Models.Move;
import Models.LatinSquare;

import java.util.HashSet;

public class FutoshikiTable extends LatinSquare {
    private final FutoCoinstraintTable constraintTable;

    public FutoshikiTable(int[][] table, HashSet<Integer>[][] domain, FutoCoinstraintTable constraintTable, boolean forwardChecking) {
        super(table, domain, forwardChecking);
        this.constraintTable = constraintTable;
    }
    public FutoshikiTable(int[][] table, FutoCoinstraintTable constraintTable, boolean forwardChecking) {
        super(table, forwardChecking);
        this.constraintTable = constraintTable;
    }


    @Override
    public boolean checkCell(int x, int y) {
        return checkNeighborhood(x,y) && super.checkCell(x, y) ;

    }

    @Override
    public LatinSquare doMove(Move move) {
        return new FutoshikiTable(tableAfterMove(move),domainAfterMove(move), constraintTable, forwardChecking);
    }

    @Override
    public void deleteFromDomainBacktrace(Move move) {
        deleteFromDomain(move.getX(),move.getY(),move.getCell(),domain);
    }

    public void deleteFromDomainInit(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(table[i][j]>0){
                    domain = domainAfterMove(new Move(j,i,table[i][j]));
                }
                if(!domain[i][j].isEmpty()) checkCoinstraints(j,i,domain);
            }
        }
    }

    @Override
    protected HashSet<Integer>[][] domainAfterMove(Move move) {
        int x = move.getX();
        int y = move.getY();
        HashSet<Integer>[][] result = cloneDomain();
        result[y][x].clear();
        if(forwardChecking) {
            for (int i = 0; i < size; i++) {
                deleteFromDomain(x, i, move.getCell(), result);
                deleteFromDomain(i, y, move.getCell(), result);
            }
            if (x > 0) {
                deleteFromDomainInsert(x - 1, y, move.getCell(), constraintTable.getCoinstraint(x, y, x - 1, y), result);
            }
            if (x < size - 1) {
                deleteFromDomainInsert(x + 1, y, move.getCell(), constraintTable.getCoinstraint(x, y, x + 1, y), result);
            }
            if (y > 0) {
                deleteFromDomainInsert(x, y - 1, move.getCell(), constraintTable.getCoinstraint(x, y, x, y - 1), result);
            }
            if (y < size - 1) {
                deleteFromDomainInsert(x, y + 1, move.getCell(), constraintTable.getCoinstraint(x, y, x, y + 1), result);
            }
        }
        return result;
    }

    private void deleteFromDomainInsert(int x, int y, int value, int constraint, HashSet<Integer>[][] dom){
        if(constraint==-1){
            for (int i = 0; i <= value; i++) {
                if(dom[y][x].contains(i)){
                    deleteFromDomain(x,y,i,dom);
                }
            }
        }
        if(constraint==1){
            for(int i = value; i<=size; i++){
                if(dom[y][x].contains(i)){
                    deleteFromDomain(x,y,i,dom);
                }
            }
        }
    }

    private void deleteFromDomain(int x, int y, int value, HashSet<Integer>[][] dom){
        if(forwardChecking) {
            if (dom[y][x].contains(value)) {
                //System.out.print("" + value + " (" + x + "," + y + ") ");
                dom[y][x].remove(value);
                checkCoinstraints(x, y, dom);
            }
        } else dom[y][x].remove(value);
    }

    private void checkCoinstraints(int x, int y, HashSet<Integer>[][] dom){
        int largestValue = 0;
        int smallestValue = size+1;
        for(int d : dom[y][x]){
            largestValue = d > largestValue ? d : largestValue;
            smallestValue = d < smallestValue ? d : smallestValue;
        }
        if(x>0){
            checkCoinstraintNeighbor(x-1,y,smallestValue,largestValue,
                    constraintTable.getCoinstraint(x, y, x - 1, y),dom);
        }
        if(x<size-1){
            checkCoinstraintNeighbor(x+1,y,smallestValue,largestValue,
                    constraintTable.getCoinstraint(x, y, x + 1, y),dom);
        }
        if(y>0){
            checkCoinstraintNeighbor(x,y-1,smallestValue,largestValue,
                    constraintTable.getCoinstraint(x, y, x, y - 1),dom);
        }
        if(y<size-1){
            checkCoinstraintNeighbor(x,y+1,smallestValue,largestValue,
                    constraintTable.getCoinstraint(x, y, x, y+1),dom);
        }
    }

    private void checkCoinstraintNeighbor(int x, int y,int smallest, int largest, int coistraint, HashSet<Integer>[][] dom){

        if(coistraint==-1){
            for (int i = 1; i <= smallest; i++) {
                deleteFromDomain(x,y,i,dom);
            }
        }
        if(coistraint==1){
            for (int i = largest; i <= size; i++) {
                deleteFromDomain(x,y,i,dom);
            }
        }

    }

    private boolean checkNeighborhood(int x, int y){
        if(x>0){
            if(!checkNeighbor(x,y,x-1,y)) return false;
        }
        if(x<size-1){
            if(!checkNeighbor(x,y,x+1,y)) return false;
        }
        if(y>0){
            if(!checkNeighbor(x,y,x,y-1)) return false;
        }
        if(y<size-1){
            if(!checkNeighbor(x,y,x,y+1)) return false;
        }
        return true;
    }

    private boolean checkNeighbor(int x, int y, int xNeighbor, int yNeighbor){
        int cell = table[y][x];
        int nCell = table[yNeighbor][xNeighbor];
        int coinstraint = constraintTable.getCoinstraint(x,y,xNeighbor,yNeighbor);
        if (nCell==0 || coinstraint == 0) return true;
        return (cell > nCell ? 1 : -1 )==coinstraint;
    }
}
