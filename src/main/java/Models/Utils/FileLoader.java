package Models.Utils;


import Models.LatinSquare;

import java.io.IOException;

public  interface FileLoader {
    public abstract LatinSquare loadFile(int size, int level, boolean forwardChecking) throws IOException;
}
