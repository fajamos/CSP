package Models;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;

public abstract class LatinSquare {
    public final int size;
    public final int[][] table;
    @Getter protected HashSet<Integer>[][] domain;

    public final boolean forwardChecking;

    public LatinSquare(int[][] table, HashSet<Integer>[][] domain, boolean forward_checking){
        this.size = table.length;
        this.domain = domain;
        this.table = table;
        forwardChecking = forward_checking;
    }

    public LatinSquare(int[][] table, boolean forward_checking){
        this.size = table.length;
        forwardChecking = forward_checking;
        domain = fullDomain(size);
        this.table = table;
    }

    public abstract LatinSquare doMove(Move move);

    public Solution checkForSulution(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(table[i][j]==0) return null;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if((!checkCell(j,i)) || (!domain[i][j].isEmpty())) return null;
            }
        }
        return new Solution(table);
    }

    public boolean checkCell(int x, int y){
        int cell = table [y][x];
        if (cell<1 || cell > size){
            return false;
        }
        for(int i=0; i< size ; i++){
            if(x!=i){
                if (table[y][i]==cell) return false;
            }
            if(y!=i){
                if (table[i][x]==cell) return false;
            }
        }
        return true;
    }

    public boolean isValid(Move move){
        return checkCell(move.getX(),move.getY()) && isValid();
    }
    public boolean isValid(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(domain[i][j].isEmpty() && table[i][j]==0) return false;
            }
        }
        return true;
    }


    public abstract void deleteFromDomainBacktrace(Move move);
    public abstract void deleteFromDomainInit();
    public void deleteFromDomainInitNoForwardChecking(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(table[i][j]!=0){
                    domain[i][j].clear();
                }
            }
        }
    };

    protected abstract HashSet<Integer>[][] domainAfterMove(Move move);

    protected int[][] tableAfterMove(Move move){
        int[][] result = new int[size][];
        for (int i = 0; i < size; i++) {
            result[i] = Arrays.copyOf(table[i],size);
        }
        result[move.getY()][move.getX()] = move.getCell();
        return result;
    }

    private HashSet<Integer>[][] fullDomain(int size){
        HashSet<Integer>[][] domain = new HashSet[size][size];
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                domain[i][j] = new HashSet<>(size);
                for(int k = 1; k<=size; k++){
                    domain[i][j].add(k);
                }
            }
        }
        return domain;
    }

    protected HashSet<Integer>[][] cloneDomain(){
        HashSet<Integer>[][] result = new HashSet[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = new HashSet<>(domain[i][j]);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for(int[] row : table){
            for(int i : row){
                result += i +" ";
            }
            result += "\n";
        }
        for (int i = 0; i < size; i++) {
            result += Arrays.deepToString(domain[i]) + "\n";
        }
        return result + "\n";
    }
}
