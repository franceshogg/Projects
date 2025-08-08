package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveFile implements Serializable {
    public final TETile[][] world;
    public final TERenderer ter;
    public final int score;
    public final int seed;
    public ArrayList<Integer> avatarLoc;
    public int foodX;
    public int foodY;

    public SaveFile(TETile[][] world, TERenderer ter, int score, int seed, ArrayList<Integer> avatarLoc, int foodX, int foodY) {
        this.world = world;
        this.ter = ter;
        this.score = score;
        this.seed = seed;
        this.avatarLoc = avatarLoc;
        this.foodX = foodY;
        this.foodY = foodY;
    }
}
