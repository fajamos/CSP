package Models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Move {
    private final int x;
    private final int y;
    private final int cell;

    @Override
    public String toString() {
        return String.format("MOVE: x:%x y:%d value:%d",x,y,cell);
    }
}
