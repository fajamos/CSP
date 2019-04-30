package Models.Utils;

import Models.Futoshiki.FutoCoinstraintTable;
import Models.Futoshiki.FutoshikiTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FutoshikiLoader implements FileLoader {
    private static final int ASCII_1_CHAR = 65;
    private static final int ASCII_A_CHAR = 49;
    private int[][] table;
    private FutoCoinstraintTable coinstraintTable;


    public FutoshikiTable loadFile(int size, int level, boolean forwardChecking) throws IOException {
        File file = new File("src/main/resources/test_futo_"+size+"_"+level+".txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        coinstraintTable = new FutoCoinstraintTable(size);
        table = new int[size][];
        reader.readLine();
        reader.readLine();
        for(int i=0; i<size;i++){
            table[i] = Arrays.stream(reader.readLine().split(";")).mapToInt(Integer::parseInt).toArray();
        }
        reader.readLine();
        String row;
        while((row = reader.readLine()) != null && !row.equals("\n")){
            readCoinstraint(row);
        }
        FutoshikiTable result = new FutoshikiTable(table,coinstraintTable, forwardChecking);
        return result;
    }

    private void readCoinstraint(String row){
        String cell1, cell2;
        int x1, x2, y1, y2;
        cell1 = row.toUpperCase().split(";")[0];
        cell2 = row.toUpperCase().split(";")[1];
        y1 = cell1.charAt(0) - ASCII_1_CHAR;
        x1 = cell1.charAt(1) - ASCII_A_CHAR;
        y2 = cell2.charAt(0) - ASCII_1_CHAR;
        x2 = cell2.charAt(1) - ASCII_A_CHAR;
        coinstraintTable.addCoinstraint(x1,y1,x2,y2);
    }




}
