package Models.Skyscraper;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class SkyscraperCoinstraint {
    private final int[] top;
    private final int[] bottom;
    private final int[] left;
    private final int[] right;

    public int top(int x){
        return top[x];
    }
    public int bottom(int x){
        return bottom[x];
    }
    public int right(int y){
        return right[y];
    }
    public int left(int y){
        return left[y];
    }

    @Override
    public String toString() {
        String result = "T: " + String.join(", ", Arrays.toString(top)) + "\n";
        result += "L: " + String.join(", ", Arrays.toString(left)) + "\n";
        result += "R: " + String.join(", ", Arrays.toString(right)) + "\n";
        result += "B: " + String.join(", ", Arrays.toString(bottom)) + "\n";

        return result;
    }
}
