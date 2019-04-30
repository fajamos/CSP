package Models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.stream.Stream;

@AllArgsConstructor
@EqualsAndHashCode
public class Solution {
    private int[][] table;

    @Override
    public String toString() {
        String result = "";
        for(int[] row : table){
            for(int i : row){
                result += i +" ";
            }
            result += "\n";
        }
        return result;
    }
}

