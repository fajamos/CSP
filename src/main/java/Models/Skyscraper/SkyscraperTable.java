package Models.Skyscraper;

import Models.LatinSquare;
import Models.Move;

import java.util.HashSet;

public class SkyscraperTable extends LatinSquare {
    private final SkyscraperCoinstraint coinstraint;

    public SkyscraperTable(int[][] table, HashSet<Integer>[][] domain, boolean forward_checking, SkyscraperCoinstraint coinstraint) {
        super(table, domain, forward_checking);
        this.coinstraint = coinstraint;
    }

    public SkyscraperTable(int[][] table, boolean forward_checking, SkyscraperCoinstraint coinstraint) {
        super(table, forward_checking);
        this.coinstraint = coinstraint;
    }

    @Override
    public LatinSquare doMove(Move move) {
        return new SkyscraperTable(tableAfterMove(move),domainAfterMove(move),forwardChecking,coinstraint);
    }

    @Override
    public void deleteFromDomainBacktrace(Move move) {
        deleteFromDomain(move.getX(),move.getY(),move.getCell(),domain);
    }

    private void deleteFromDomain(int x, int y, int value, HashSet<Integer>[][] dom){
        dom[y][x].remove(value);
    }

    @Override
    protected HashSet<Integer>[][] domainAfterMove(Move move) {
        int x = move.getX();
        int y = move.getY();
        HashSet<Integer>[][] result = cloneDomain();
        result[y][x].clear();
        if (forwardChecking){
            for (int i = 0; i < size; i++) {
                deleteFromDomain(x, i, move.getCell(), result);
                deleteFromDomain(i, y, move.getCell(), result);
            }
        }
        return result;
    }

    public void deleteFromDomainInit(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(table[i][j]!=0){
                    domain = domainAfterMove(new Move(j,i,table[i][j]));
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if(coinstraint.top(i)==1){
                for (int d = 1; d < size; d++) {
                    deleteFromDomain(i,0,d,domain);
                }
            } else for (int valueToDelete = size; valueToDelete > size + 1 - coinstraint.top(i); valueToDelete--) {
                for (int distance = 0; distance < valueToDelete + coinstraint.top(i) - size - 1; distance++) {
                    deleteFromDomain(i,distance,valueToDelete,domain);
                }
            }
            if(coinstraint.bottom(i)==1){
                for (int d = 1; d < size; d++) {
                    deleteFromDomain(i,size-1,d,domain);
                }
            } else for (int valueToDelete = size; valueToDelete > size + 1 - coinstraint.bottom(i); valueToDelete--) {
                for (int distance = 0; distance < valueToDelete + coinstraint.bottom(i) - size - 1; distance++) {
                    deleteFromDomain(i, size - 1 - distance, valueToDelete, domain);
                }
            }
            if(coinstraint.left(i)==1){
                for (int d = 1; d < size; d++) {
                    deleteFromDomain(0,i,d,domain);
                }
            } else for (int valueToDelete = size; valueToDelete > size + 1 - coinstraint.left(i); valueToDelete--) {
                for (int distance = 0; distance < valueToDelete + coinstraint.left(i) - size - 1; distance++) {
                    deleteFromDomain(distance, i, valueToDelete, domain);
                }
            }
            if(coinstraint.right(i)==1){
                for (int d = 1; d < size; d++) {
                    deleteFromDomain(size-1,i,d,domain);
                }
            } else for (int valueToDelete = size; valueToDelete > size + 1 - coinstraint.right(i); valueToDelete--) {
                for (int distance = 0; distance < valueToDelete + coinstraint.right(i) - size -1; distance++) {
                    deleteFromDomain(size - 1 - distance, i, valueToDelete, domain);
                }
            }
        }
    }
//
//    public void deleteFromDomainInit(){
//        for (int i = 0; i < size; i++) {
//            if(coinstraint.top(i)==1){
//                for (int d = 1; d < size; d++) {
//                    deleteFromDomain(i,0,d,domain);
//                }
//            }
//            if(coinstraint.bottom(i)==1){
//                for (int d = 1; d < size; d++) {
//                    deleteFromDomain(i,size-1,d,domain);
//                }
//            }
//            if(coinstraint.left(i)==1){
//                for (int d = 1; d < size; d++) {
//                    deleteFromDomain(0,i,d,domain);
//                }
//            }
//            if(coinstraint.right(i)==1){
//                for (int d = 1; d < size; d++) {
//                    deleteFromDomain(size-1,i,d,domain);
//                }
//            }
//        }
//    }

    @Override
    public boolean isValid(Move move) {
        return checkCoinstraints(move) && super.isValid(move);
    }

    private boolean checkCoinstraints(Move move){
        int topMax = 0;
        int topCount = 0;
        int bottomMax = 0;
        int bottomCount = 0;
        int leftMax = 0;
        int leftCount = 0;
        int rightMax = 0;
        int rightCount = 0;
        boolean hasBlanksHorizontal = false;
        boolean hasBlanksVertical = false;
        for (int i = 0; i < size; i++) {
            int left = table[move.getY()][i];
            int right = table[move.getY()][size - i - 1];
            int top = table[i][move.getX()];
            int bottom = table[size - i -1][move.getX()];
            if(left==0){
                hasBlanksHorizontal = true;
            } else if(left>leftMax){
                leftCount++;
                leftMax = left;
            }
            if(right==0){
                hasBlanksHorizontal = true;
            } else if(right>rightMax){
                rightCount++;
                rightMax = right;
            }
            if(top==0){
                hasBlanksVertical = true;
            } else if(top>topMax){
                topCount++;
                topMax = top;
            }
            if(bottom==0){
                hasBlanksVertical = true;
            } else if(bottom>bottomMax){
                bottomCount++;
                bottomMax = bottom;
            }
            if (hasBlanksHorizontal&&hasBlanksVertical) return true;
        }
        if(!hasBlanksHorizontal){
            if(coinstraint.left(move.getY()) != 0 && leftCount!=coinstraint.left(move.getY())) return false;
            if(coinstraint.right(move.getY()) != 0 && rightCount!=coinstraint.right(move.getY())) return false;
        }

        if(!hasBlanksVertical){
            if(coinstraint.top(move.getX()) != 0 && topCount!=coinstraint.top(move.getX())) return false;
            if(coinstraint.bottom(move.getX()) != 0 && bottomCount!=coinstraint.bottom(move.getX())) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + coinstraint.toString();
    }
}
