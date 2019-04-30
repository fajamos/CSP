package Models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Getter
public class State {
    private final State parent;
    private final LatinSquare level;
    private final Move move;

    public String levelString(){
        String result = "";

        result += level.toString();

        if(move!=null) {
            result += move.toString() + " ";
            for(int i :  parent.level.getDomain()[move.getY()][move.getX()]){
                result += String.format("%d, ", i);
            }

        }
        return result;

    }


}
