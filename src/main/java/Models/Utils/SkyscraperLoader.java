package Models.Utils;

import Models.LatinSquare;
import Models.Skyscraper.SkyscraperCoinstraint;
import Models.Skyscraper.SkyscraperTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SkyscraperLoader implements FileLoader {
    @Override
    public LatinSquare loadFile(int size, int level, boolean forwardChecking) throws IOException {
        File file = new File("src/main/resources/test_sky_"+size+"_"+level+".txt");
        int[][] coinstraints = new int[4][size];
        int[][] table = new int[size][size];
        for(int[] row : table){
            Arrays.fill(row,0);
        }
        SkyscraperCoinstraint skyCoinstraint;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine();
        for (int i = 0; i < 4; i++) {
            String[] arr = reader.readLine().split(";");
            for (int j = 1; j <= size; j++) {
                coinstraints[i][j-1]=Integer.parseInt(arr[j]);
            }
        }
        skyCoinstraint = new SkyscraperCoinstraint(coinstraints[0],coinstraints[1],coinstraints[2],coinstraints[3]);
        SkyscraperTable result = new SkyscraperTable(table,forwardChecking,skyCoinstraint);
        return result;

    }
}
